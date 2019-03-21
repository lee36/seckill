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
            isShop:"",
            userList:"",
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
        loadUserList:function(self){
            axios.get("http://localhost:8080/sys/userList?page="+self.page+"&size="+self.size)
                .then(function(response){
                    let obj=response.data
                    if(obj.code==500){
                        alert("没有任何内容")
                        return ;
                    }else{
                        self.userList=obj.data.list;
                        self.totalPage=obj.data.pages;
                        return ;
                    }

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
        jump:function(item){
            this.page=item;
            this.loadUserList(this);
        },
        before:function(){
            if(this.page==1){
                return ;
            }
            this.page=this.page-1;
            this.loadUserList(this);
        },
        next:function(){
            if(this.page==this.totalPage){
                return ;
            }
            this.page=this.page+1
            this.loadUserList(this);
        },
        delUser:function(){
           let length=this.checked.length;
           if(length==0){
               alert("请选择你要删除的对象!")
               return ;
           }
           let flag=window.confirm("你确定要删除你选中的内容吗?")
           if(flag){
               self=this;
               axios.get("http://localhost:8080/sys/delUser?users="+JSON.stringify(self.checked))
                   .then(function(response){
                       let obj=response.data
                       if(obj.code==200){
                           alert("恭喜你删除成功");
                           window.location.reload();
                           return ;
                       }
                       alert("删除失败")
                       return ;
                   })
           }
        },
        updateUser:function(){
            let length=this.checked.length;
            if(length==0){
                alert("请选择你要修改的对象!")
                return ;
            }
            if(length>1){
                alert("一次只能修改一个")
                return ;
            }
            self=this;
            axios.get("http://localhost:8080/sys/getUser?id="+self.checked[0])
                .then(function(response){
                    let obj=response.data;
                    console.log(obj)
                    if(obj.data.identity==4){
                        alert("游客信息不能修改")
                        return ;
                    }
                    if(obj.code==200){
                        self.fixUser=obj.data;
                        console.log(self.fixUser)
                        self.isShowUpdate=true;
                        return ;
                    }else{
                        alert("加载数据异常")
                        return ;
                    }
                })
        },
        cageUser:function(flag){
            msg="解封";
            if(flag==1){
                msg="冻结";
            }
            let length=this.checked.length;
            if(length==0){
                alert("请选择你要"+msg+"的对象!")
                return ;
            }
            let b=window.confirm("你确定要"+msg+"你选中的内容吗?")
            if(b){
                self=this;
                axios.get("http://localhost:8080/sys/cageUser?ids="+JSON.stringify(self.checked)+"&flag="+flag)
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
            axios.post("http://localhost:8080/sys/updateUser",{
                id:self.fixUser.id,
                phone:self.fixUser.phone,
                email:self.fixUser.email,
                status:self.fixUser.status,
                identity:self.fixUser.identity
            }).then(function(response){
                console.log(response)
                let obj=response.data;
                if(obj.code==200){
                    alert("修改成功!")
                    self.isShowUpdate=false;
                    window.location.reload();
                    return ;
                }
                alert("失败")
                return ;
            })
        }
    },
    created:function(){
        this.loadUser(this);
        this.loadUserList(this);
    }
})