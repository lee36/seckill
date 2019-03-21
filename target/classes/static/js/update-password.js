new Vue({
    el:"#app",
    data(){
        return {
            password:"",
            rePassword:""
        }
    },
    methods:{
        updatePass:function() {
            if (this.password != this.rePassword) {
                //
                alert("用户名密码不正确")
                return;
            }
            axios.post("http://localhost:8080/sys/newPass",{password:this.password,rePassword:this.rePassword})
                .then(function (response) {
                    console.log(response)
                    let result=response.data.code
                    alert(result)
                    if(result==200){
                       alert("修改成功,即将跳转到登录页面");
                       window.location.href="http://localhost:8080/html/login.html"
                    }else{
                        alert("操作异常")
                    }
                })
        }
    }
})