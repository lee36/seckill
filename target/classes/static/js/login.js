new Vue({
	el:"#app",
	data(){
        return {
          username_info:"",
          password_info:"",
          username:"",
          password:""
        }
	},
	methods:{
       upToSubmit:function(){
            let username=this.username;
            let password=this.password;
            if(username==''||username==null){
               this.username_info="用户名不能为空"
               return ;
            }
            if(password==""||password==null){
               this.password_info="密码不能为空"
               return ;
            }
            axios
            .post("http://localhost:8080/sys/login",{username:username,password:password})
            .then(function(response){
                let result=response.data;
                if(result.code==200){
                    //登录成功
                    alert("登录成功")
                    //跳转
                    window.location.href="http://localhost:8080/html/index.html"
                }else if(result.code==501){
                    alert("账号被冻结了，请联系管理员")
                    this.clearForm();
                }else if(result.code==503){
                    alert("账号或者密码格式不正确")
                    this.clearForm();
                }else{
                    alert("账号密码错误")
                    this.clearForm();
                }
            })
       },
       clearForm:function(){
             this.username=""
             this.password=""
       },
       forgetPass:function(){
           window.location.href="http://localhost:8080/html/forget.html"
       }
	}
})