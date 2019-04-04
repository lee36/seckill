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
            catalogList:"",
            page:1,
            size:4,
            totalPage:"",
            checked:[],
            fixUser:{},
            isShowUpdate:false,
            isShowUpdate1:false
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
        loadCatalogList:function(self){
            axios.get("http://localhost:8080/sys/catalogList?page="+self.page+"&size="+self.size)
                .then(function(response){
                    let obj=response.data
                    if(obj.code==500){
                        return ;
                    }else{
                        self.catalogList=obj.data.list;
                        self.totalPage=obj.data.pages;
                        return ;
                    }

                });
        },
        addCatalog:function(){
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
            this.loadCatalogList(this);
        },
        before:function(){
            if(this.page==1){
                return ;
            }
            this.page=this.page-1;
            this.loadCatalogList(this);
        },
        next:function(){
            if(this.page==this.totalPage){
                return ;
            }
            this.page=this.page+1
            this.loadCatalogList(this);
        },
        delCatalog:function(){
            let length=this.checked.length;
            if(length<=0){
                alert("请选择你要删除的对象!")
                return ;
            }
            let flag = window.confirm("你确定要删除你选中的内容吗?")
            if (flag) {
                self = this;
                axios.get("http://localhost:8080/sys/delCatalog?catalogs=" + JSON.stringify(self.checked))
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
        updateCatalog:function(){
            let length=this.checked.length;
            if(length<=0){
                alert("请选择你要修改的对象!")
                return ;
            }
            self=this;
            axios.get("http://localhost:8080/sys/getCatalog?id="+self.checked[0])
                .then(function (response) {
                    let obj = response.data
                    if (obj.code == 200) {
                        console.log(response)
                        //复制
                        console.log(obj.data)
                        self.fixUser.name=obj.data.name;
                        self.fixUser.info=obj.data.info;
                        self.fixUser.icon=obj.data.icon;
                        self.fixUser.id=obj.data.id;
                        self.isShowUpdate1=true;
                        return;
                    }
                    alert("加载失败")
                    return;
                })

        },
        closeFrame:function(){
            this.isShowUpdate=false;
            return ;
        },
        closeFrame1:function(){
            this.isShowUpdate1=false;
            return ;
        },
        submitFrame:function(){
            self=this;
            var formData = new FormData();
            var obj=document.getElementById("update_bachPic")
            formData.append("file",obj.files[0]);
            formData.append("name",$("#name").val());
            formData.append("info",$("#info").val());
            $.ajax({
                type: 'post',
                url: "http://localhost:8080/sys/addCatalog",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success:function(result){
                    console.log(result)
                    let obj=result.code;
                    if(obj==200){
                        alert("上传成功")
                        self.isShowUpdate=false;
                        window.location.reload();
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
        },
        submitFrame1:function(){
            self=this;
            var formData = new FormData();
            var obj=document.getElementById("update1")
            formData.append("file",obj.files[0]);
            formData.append("id",self.fixUser.id);
            formData.append("name",self.fixUser.name);
            formData.append("info",self.fixUser.info);
            $.ajax({
                type: 'post',
                url: "http://localhost:8080/sys/updateCatalog",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success:function(result){
                    console.log(result)
                    let obj=result.code;
                    if(obj==200){
                        alert("修改成功")
                        self.isShowUpdate1=false;
                        window.location.reload();
                        return ;
                    }else{
                        alert("修改失败")
                        self.isShowUpdate1=false;
                        return ;
                    }

                },
                error:function(data){
                    alert("修改时候发生异常")
                    return ;
                }
            })
        }
    },
    created:function(){
        this.loadUser(this);
        this.loadCatalogList(this);
    }
})