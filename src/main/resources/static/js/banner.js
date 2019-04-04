new Vue({
    el:"#app",
    data(){
        return {
            user: "",
            isUser:"",
            isAdvice:"",
            isBanner:"",
            isGood:"",
            isSeckill:"",
            isCatalog:"",
            bannerList:"",
            page:1,
            size:4,
            totalPage:"",
            checked:[],
            fixUser:"",
            isShowUpdate:false,
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
        loadBannerList:function(self){
            axios.get("http://localhost:8080/sys/bannerList?page="+self.page+"&size="+self.size)
                .then(function(response){
                    let obj=response.data
                    if(obj.code==500){
                        return ;
                    }else{
                        self.bannerList=obj.data.list;
                        self.totalPage=obj.data.pages;
                        return ;
                    }

                });
        },
        addBanner:function(){
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
        jump:function(item){
            this.page=item;
            this.loadBannerList(this);
        },
        before:function(){
            if(this.page==1){
                return ;
            }
            this.page=this.page-1;
            this.loadBannerList(this);
        },
        next:function(){
            if(this.page==this.totalPage){
                return ;
            }
            this.page=this.page+1
            this.loadBannerList(this);
        },
        delBanner:function(){
            let length=this.checked.length;
            if(length<=0){
                alert("请选择你要删除的对象!")
                return ;
            }
            let flag = window.confirm("你确定要删除你选中的内容吗?")
            if (flag) {
                self = this;
                axios.get("http://localhost:8080/sys/delBanners?banners=" + JSON.stringify(self.checked))
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
        cageBanner:function(flag){
            msg="开启";
            if(flag==1){
                msg="开启";
            }else{
                msg="关闭";
            }
            let length=this.checked.length;
            if(length==0){
                alert("请选择你要"+msg+"的对象!")
                return ;
            }
            let b=window.confirm("你确定要"+msg+"你选中的内容吗?")
            if(b){
                self=this;
                axios.get("http://localhost:8080/sys/cageBanner?ids="+JSON.stringify(self.checked)+"&flag="+flag)
                    .then(function(response){
                        let obj=response.data
                        if(obj.code==200){
                            alert("恭喜你"+msg+"成功");
                            window.location.reload();
                            return ;
                        }
                        alert(msg+"失败")
                        return ;
                    })
            }
        },
        closeFrame:function(){
            this.isShowUpdate=false;
            return ;
        },
        submitFrame:function(){
            self=this;
            var formData = new FormData();
            var obj=document.getElementById("update_bachPic")
            for (var i=0;i<obj.files.length;i++) {
                formData.append("file", obj.files[i]);

            }

            $.ajax({
                type: 'post',
                url: "http://localhost:8080/sys/addBanner",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success:function(result){
                    let obj=result.code;
                    if(obj==200){
                        alert("上传成功")
                        window.location.reload()
                        self.isShowUpdate=false;
                        return ;
                    }else{
                        alert("上传失败")
                        self.isShowUpdate=false;
                        return ;
                    }

                },
                error:function(data){
                    alert("上传时候发生异常")
                    return ;
                }
            })
        }
    },
    created:function(){
        this.loadUser(this);
        this.loadBannerList(this);
    }
})