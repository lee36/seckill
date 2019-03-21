Vue.filter("timeformat",function(value){
    return value.substring(0,value.lastIndexOf("."))
})
new Vue({
    el:"#app",
    data(){
        return {
            user: {},
            isUser:"",
            isAdvice:"",
            isBanner:"",
            isGood:"",
            isSeckill:"",
            isShop:"",
            seckillList:"",
            seckill:{},
            good:"",
            goods:[],
            page:1,
            editor1:"",
            editor2:"",
            size:4,
            totalPage:"",
            checked:[],
            fixUser:"",
            isShowUpdate:false,
            showFlag:false
        }
    },
    methods:{
        loadUser:function(self){
            axios.get("http://localhost:8080/sys/current")
                .then(function(response){
                    self.user=response.data.data;
                    console.log(self.user)
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
                    self.loadMySelfGoods(self);
                    self.loadSeckillList(self);
                });
        },
        loadSeckillList:function(self){
            axios.get("http://localhost:8080/seckill/myselfSeckill?page="+self.page+"&size="+self.size+"&id="+self.user.id)
                .then(function(response){
                    let obj=response.data
                    if(obj.code==500){
                        alert("没有任何内容")
                        return ;
                    }else{
                        self.seckillList=obj.data.list;
                        self.totalPage=obj.data.pages;
                        return ;
                    }

                });
        },
        addSeckill:function(){
            this.isShowUpdate=true;
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
        jump:function(item){
            this.page=item;
            this.loadSeckillList(this);
        },
        before:function(){
            if(this.page==1){
                return ;
            }
            this.page=this.page-1;
            this.loadSeckillList(this);
        },
        next:function(){
            if(this.page==this.totalPage){
                return ;
            }
            this.page=this.page+1
            this.loadSeckillList(this);
        },
        loadMySelfGoods:function(self){
            axios.get("http://localhost:8080/sys/getMyGoods?id="+self.user.id)
                .then(function(result){
                    let obj=result.data;
                    if(obj.code==200){
                        self.goods=obj.data;
                        return ;
                    }
                    return ;
                })
        },
        delSeckill:function(){
            let length=this.checked.length;
            if(length<=0){
                alert("请选择你要删除的对象!")
                return ;
            }
            let flag = window.confirm("你确定要删除你选中的内容吗?")
            if (flag) {
                self = this;
                axios.get("http://localhost:8080/seckill/delSeckill?seckills=" + JSON.stringify(self.checked))
                    .then(function (response) {
                        let obj = response.data
                        if (obj.code == 200) {
                            alert("恭喜你删除成功");
                            window.location.reload();
                            return;
                        }
                        alert("删除失败")
                        return;
                    })
            }
        },
        updateGoods:function(){
            var E = window.wangEditor;
            if(this.editor2==""){
                this.editor2 = new E('#editor2')
                this.editor2.customConfig.uploadImgShowBase64 = true
                this.editor2.create();
            }
            //加载数据
            self=this;
            let length=this.checked.length;
            if(length<=0){
                alert("请选择你要修改的对象!")
                return ;
            }
            if(length>1){
                alert("一次只能修改一个对象!")
                return ;
            }
            axios.get("http://localhost:8080/sys/getGoods?id="+self.checked[0])
                .then(function(result){
                   console.log(result);
                   let obj=result.data;
                   if(obj.code==200){
                       self.good.id=obj.data.id;
                       self.good.name=obj.data.name;
                       self.good.img=obj.data.img;
                       self.good.price=(obj.data.price)/1000.0;
                       self.good.stock=obj.data.stock;
                       self.good.info=obj.data.info;
                       self.good.status=obj.data.status;
                       self.good.catalogId=obj.data.goodsCatalog.id;
                       self.editor2.txt.html(obj.data.detail);
                       self.showFlag=true;
                   }
                })
        },
        closeFrame:function(){
            this.isShowUpdate=false;
            return ;
        },
        submitFrame:function(){
            self=this;
            console.log(self.seckill)
            axios.post("http://localhost:8080/seckill/add",{
                "goodId":self.seckill.goodId,
                "price":(self.seckill.price)*1000.0,
                "stock":self.seckill.stock,
                "createTime":self.seckill.createTime,
                "endTime":self.seckill.endTime,
            }).then(function(response){
                let obj=response.data;
                if(obj.code==200){
                    alert("添加成功");
                    self.isShowUpdate=false;
                    window.location.reload();
                    return ;
                }
                alert("添加失败")
                return ;
            })
        },
        submitFrame1:function() {
            self = this;
            var formData = new FormData();
            var obj = document.getElementById("upload1")
            formData.append("file", obj.files[0]);
            formData.append("name", self.good.name);
            formData.append("id", self.good.id);
            formData.append("price", (self.good.price)*1000.0);
            formData.append("stock", self.good.stock);
            formData.append("info", self.good.info);
            formData.append("detail", self.editor2.txt.html());
            formData.append("goodsCatalog.id", self.good.catalogId);
            formData.append("status", self.good.status);
            $.ajax({
                type: 'post',
                url: "http://localhost:8080/sys/updateGoods",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success: function (result) {
                    console.log(result);
                    let obj = result.code;
                    if (obj == 200) {
                        alert("更新成功")
                        self.isShowUpdate = false;
                        window.location.reload();
                        return;
                    } else {
                        alert("更新失败")
                        self.isShowUpdate = false;
                        return;
                    }

                },
                error: function (data) {
                    alert("更新时候发生异常")
                    return;
                }
            })
        },
        closeFrame1:function(){
            this.showFlag=false;
        }
    },
    created:function(){
        this.loadUser(this);

    }
})