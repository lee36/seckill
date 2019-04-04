new Vue({
	el:"#app",
	data(){
		return {
            user: "",
			totalUser:"",
			times:"",
            nums:"",
            isUser:"",
            isAdvice:"",
            isBanner:"",
            isGood:"",
            isSeckill:"",
            isCatalog:"",
            isErea:""

        }
	},
	methods:{
	   loadUser:function(self){
           axios.get("http://localhost:8080/sys/current")
               .then(function(response){
                   self.user=response.data.data;
                   if(self.user==null){
                       window.location.href="http://localhost:8080/html/login.html"
                       return ;
                   }
                   self.isShowUser(self.user);
                   self.isShowGood(self.user);
                   self.isShowAdvice(self.user);
                   self.isShowBanner(self.user);
                   self.isShowSeckill(self.user);
                   self.isShowCatalog(self.user);
               });
	   },
	   getUserNums:function(self){
           axios.get("http://localhost:8080/sys/getuserNums")
               .then(function(response){
                   self.totalUser=response.data.data
               });
	   },
	   getLastSixFlow:function(self){
	   	 axios.get("http://localhost:8080/sys/lastSixFlow")
			 .then(function(response){
			 	self.times=response.data.data.times;
			 	self.nums=response.data.data.nums;
			 	//加载图
                 var lineChart = $('#line-chart');

                 if (lineChart.length > 0) {
                     new Chart(lineChart, {
                         type: 'line',
                         data: {
                             labels: self.times,
                             datasets: [{
                                 label: '一周内流量统计',
                                 data: self.nums,
                                 backgroundColor: 'rgba(66, 165, 245, 0.5)',
                                 borderColor: '#2196F3',
                                 borderWidth: 1
                             }]
                         },
                         options: {
                             legend: {
                                 display: false
                             },
                             scales: {
                                 yAxes: [{
                                     ticks: {
                                         beginAtZero: true
                                     }
                                 }]
                             }
                         }
                     });
                 }
			 })
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
        isShowCatalog:function(user){
            let identity=user.identity;
            if(identity==2){
                this.isCatalog=true;
            }else{
                this.isCatalog=false;
            }
        },
        logout:function(){
            axios.get("http://localhost:8080/sys/logout")
                .then(function(response){
                    window.location.href="http://localhost:8080/html/login.html";
                })
        },
        randomData:function() {
          return Math.round(Math.random()*500);
        }
    },
    created:function(){
        this.loadUser(this);
        this.getUserNums(this);
        this.getLastSixFlow(this);
    }
})