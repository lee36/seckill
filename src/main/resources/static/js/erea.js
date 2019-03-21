new Vue({
    el:"#app",
    data(){
        return {
            mydata:"",
            user: "",
            isUser:"",
            isAdvice:"",
            isBanner:"",
            isGood:"",
            isSeckill:"",
            isShop:"",
        }
    },
    methods: {
        load:function(){
            //加载数据
            axios.get("http://localhost:8080/sys/groupByCity")
                .then(function(result){
                    let obj=result.data
                    if(obj.code==200){
                      self.mydata=obj.data;
                        var optionMap = {
                            backgroundColor: '#FFFFFF',
                            title: {
                                text: '系统人口分布图',
                                subtext: '',
                                x:'center'
                            },
                            tooltip : {
                                trigger: 'item'
                            },

                            //左侧小导航图标
                            visualMap: {
                                show : true,
                                x: 'right',
                                y: 'center',
                                splitList: [
                                    {start: 500, end:600},{start: 400, end: 500},
                                    {start: 300, end: 400},{start: 200, end: 300},
                                    {start: 100, end: 200},{start: 0, end: 100},
                                ],
                                color: ['#5475f5', '#9feaa5', '#85daef','#74e2ca', '#e6ac53', '#9fb5ea']
                            },

                            //配置属性
                            series: [{
                                name: '数据',
                                type: 'map',
                                mapType: 'china',
                                roam: true,
                                label: {
                                    normal: {
                                        show: true  //省份名称
                                    },
                                    emphasis: {
                                        show: false
                                    }
                                },
                                data:self.mydata  //数据
                            }]
                        };
                        //初始化echarts实例
                        var myChart = echarts.init(document.getElementById('main'));

                        //使用制定的配置项和数据显示图表
                        myChart.setOption(optionMap);

                    }else{
                        return ;
                    }
                })
        },
        loadUser:function(self){
            axios.get("http://localhost:8080/sys/current")
                .then(function(response){
                    self.user=response.data.data;
                    if(self.user==null){
                        alert("请先登录")
                        window.location.href="http://localhost:8080/html/login.html"
                        return ;
                    }
                    self.isShowUser(self.user);
                    self.isShowGood(self.user);
                    self.isShowAdvice(self.user);
                    self.isShowBanner(self.user);
                    self.isShowSeckill(self.user);
                    self.isShowShop(self.user);
                });
        },
        //显示用户index
        isShowUser:function(user){
            let identity=user.identity;
            if(identity==2){
                this.isUser=false;
            }else{
                this.isUser=true;
            }
        },
        isShowGood:function(user){
            let identity=user.identity;
            if(identity==2){
                this.isGood=true;
            }else{
                this.isGood=false;
            }
        },
        isShowSeckill:function(user){
            let identity=user.identity;
            if(identity==2){
                this.isSeckill=true;
            }else{
                this.isSeckill=false;
            }
        },
        isShowAdvice:function(user){
            let identity=user.identity;
            if(identity==2){
                this.isAdvice=false;
            }else{
                this.isAdvice=true;
            }
        },
        isShowBanner:function(user){
            let identity=user.identity;
            if(identity==2){
                this.isBanner=false;
            }else{
                this.isBanner=true;
            }
        },
        isShowShop:function(user){
            let identity=user.identity;
            if(identity==2){
                this.isShop=true;
            }else{
                this.isShop=false;
            }
        },
        logout:function(){
            axios.get("http://localhost:8080/sys/logout")
                .then(function(response){
                    window.location.href="http://localhost:8080/html/login.html";
                })
        },
    },
    mounted:function(){
        this.load(this)
    },
    created:function(){
        this.loadUser(this);
    }
})