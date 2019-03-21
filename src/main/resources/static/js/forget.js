new Vue({
	el:"#app",
	data(){
		return {
			email:"",
			token:"",
		}
	},
	methods:{
		sendEmail:function(){
			if(this.email==""||this.email==null){
				 alert("请输入有效的邮箱")
                 return ;
			}
           axios.get("http://localhost:8080/sys/forget?email="+this.email)
             .then(function(response){
                alert("发送成功，请前往邮箱确认")
             })
		},
		comfirmToken:function(){
			self=this;
           axios.get("http://localhost:8080/sys/forgetComfirm?token="+this.token)
             .then(function(response){
             	 alert("请重置你的密码")
                 let code=response.data.code;
                 if(code==200){
                     window.location.href="http://localhost:8080/html/update-password.html";
                     return ;
                 }else{
                     alert("操作失败，请重新操作")
                     return ;
                 }
             })
		}
	}
})