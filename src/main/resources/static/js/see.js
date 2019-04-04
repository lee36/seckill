Vue.prototype.ecplises= function (value) {
    if (!value) return ''
    if (value.length > 36) {
        return value.slice(0,36) + '...'
    }
    return value
};
new Vue({
    el: "#app",
    data() {
        return {
            user: "",
            isUser: "",
            isAdvice: "",
            isBanner: "",
            isGood: "",
            isSeckill: "",
            isCatalog: "",
            adviceList: "",
            page: 1,
            size: 4,
            totalPage: "",
            checked: [],
            fixAdvice: "",
            isShowUpdate: false,
            isShowRepay: false,
            editor:"",
            editor1:""

        }
    },
    methods: {
        loadUser: function (self) {
            axios.get("http://localhost:8080/sys/current")
                .then(function (response) {
                    self.user = response.data.data;
                    if (self.user == null) {
                        window.location.href = "http://localhost:8080/html/login.html"
                        return;
                    }
                    self.isShowUser(self.user);
                    self.isShowGood(self.user);
                    self.isShowAdvice(self.user);
                    self.isShowBanner(self.user);
                    self.isShowSeckill(self.user);
                    self.isShowCatalog(self.user);
                });
        },
        loadAdviceList: function (self) {
            axios.get("http://localhost:8080/sys/adviceList?page=" + self.page + "&size=" + self.size)
                .then(function (response) {
                    let obj = response.data
                    if (obj.code == 500) {
                        return;
                    } else {
                        self.adviceList = obj.data.list;
                        self.totalPage = obj.data.pages;
                        return;
                    }

                });
        },
        //显示用户index
        isShowUser: function (user) {
            let identity = user.identity;
            if (identity == 2) {
                this.isUser = false;
            } else {
                this.isUser = true;
            }
        },
        isShowGood: function (user) {
            let identity = user.identity;
            if (identity == 2) {
                this.isGood = true;
            } else {
                this.isGood = false;
            }
        },
        isShowSeckill: function (user) {
            let identity = user.identity;
            if (identity == 2) {
                this.isSeckill = true;
            } else {
                this.isSeckill = false;
            }
        },
        isShowAdvice: function (user) {
            let identity = user.identity;
            if (identity == 2) {
                this.isAdvice = false;
            } else {
                this.isAdvice = true;
            }
        },
        isShowBanner: function (user) {
            let identity = user.identity;
            if (identity == 2) {
                this.isBanner = false;
            } else {
                this.isBanner = true;
            }
        },
        isShowCatalog: function (user) {
            let identity = user.identity;
            if (identity == 2) {
                this.isCatalog = true;
            } else {
                this.isCatalog = false;
            }
        },
        logout: function () {
            axios.get("http://localhost:8080/sys/logout")
                .then(function (response) {
                    window.location.href = "http://localhost:8080/html/login.html";
                })
        },
        jump: function (item) {
            this.page = item;
            this.loadAdviceList(this);
        },
        before: function () {
            if (this.page == 1) {
                return;
            }
            this.page = this.page - 1;
            this.loadAdviceList(this);
        },
        next: function () {
            if (this.page == this.totalPage) {
                return;
            }
            this.page = this.page + 1
            this.loadAdviceList(this);
        },
        delAdvice: function () {
            let length = this.checked.length;
            if (length == 0) {
                alert("请选择你要删除的对象!")
                return;
            }
            let flag = window.confirm("你确定要删除你选中的内容吗?")
            if (flag) {
                self = this;
                axios.get("http://localhost:8080/sys/delAdvice?advices=" + JSON.stringify(self.checked))
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
        seeInfo: function () {
            let length = this.checked.length;
            if (length == 0) {
                alert("请选择你要查看的对象!")
                return;
            }
            if (length > 1) {
                alert("一次只能查看一个")
                return;
            }
            self = this;
            self.isShowUpdate=true;
            axios.get("http://localhost:8080/sys/getAdvice?id=" + self.checked[0])
                .then(function (response) {
                    let obj = response.data.data;
                    var E = window.wangEditor;
                    self.editor = new E('#editor')
                    self.editor.create();
                    console.log(obj);
                    self.fixAdvice.id=obj.id;
                    self.editor.txt.html(obj.content);
                })
        },
        repay: function () {
            var E = window.wangEditor;
            if(this.editor1==""){
                this.editor1 = new E('#editor1')
                this.editor1.customConfig.uploadImgServer = '/sys/upload'
                this.editor1.customConfig.uploadFileName = 'file'
                this.editor1.customConfig.uploadImgMaxSize = 500 * 1024 * 1024
                this.editor1.create();
            }
            this.isShowRepay=true;
        },
        closeFrame: function () {
            this.isShowUpdate = false;
            return;
        },
        closeFrame1: function () {
            this.isShowRepay = false;
            return;
        },
        submitFrame1:function(){
            self=this;
            axios.post("http://localhost:8080/sys/refAdvice/"+self.checked[0],{
                content:self.editor1.txt.html(),
                from:self.user.id,
                state:1
            }).then(function(result){
                let obj=result.data;
                if(obj.code==200){
                    alert("回复成功!")
                    self.isShowRepay=false;
                    return ;
                }else{
                    alert("回复失败!")
                    self.isShowRepay=false;
                    return ;
                }
            })
        }
    },
    created: function () {
        this.loadUser(this);
        this.loadAdviceList(this);
    }
})