<template id="home-view">
  <nav-bar>
    <div class="container" id="firstHeaderGap">
      <form>

        <div class="form-group row">
          <label for="inputEmail" class="col-sm-2 form-control-label">Email</label>
          <div class="col-sm-10">
            <input type="email" class="form-control" id="inputEmail"
                   v-model="inputData.email" name="email" placeholder="Enter your email">
          </div>
        </div>

        <div class="form-group row">
          <label for="inputPassword" class="col-sm-2 form-control-label">Password</label>
          <div class="col-sm-10">
            <input type="password" class="form-control" id="inputPassword"
                   v-model="inputData.password" name="password" placeholder="Enter your password">
          </div>
        </div>

      </form>
      <button type="button" class="btn btn-outline-primary"
              @click="verifyUser()">Submit</button>
    </div>

    <div class="card-header" id="topMargin" >
      <div class="row" >
        <div class="col-6" id="textColor"> create account</div>
        <div class="col" align="right">
          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="hideAccountForm =!hideAccountForm">
            <i class="fa fa-plus" aria-hidden="true"></i>
          </button>
        </div>
      </div>
    </div>

    <div class="card-body" :class="{ 'd-none': hideAccountForm}">
      <form id="addUser">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-user-id">Username</span>
          </div>
          <input type="text" class="form-control" v-model="createAccountData.username"
                 name="username" placeholder="Enter your name"/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-user-name">Email</span>
          </div>
          <input type="email" class="form-control" v-model="createAccountData.email"
                 name="email" placeholder="Enter your email"/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-user-email">Password</span>
          </div>
          <input type="password" class="form-control" v-model="createAccountData.password"
                 name="password" placeholder="Enter your password"/>
        </div>
      </form>
      <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link"
              @click="addUser()">Create Account</button>
    </div>
  </nav-bar>
</template>

<script>
Vue.component("home-view",{
  template: "#home-view",
  data: () => ({
    inputData: [],
    createAccountData: [],
    hideAccountForm :true
  }),
  methods: {
    verifyUser : function () {
      const userEmail = this.inputData.email;
      const url = `/api/users/mail/${userEmail}`

      axios.get(url)
          .then(response => {
            console.log(response.status);
            if (response.status == 200 && response.data.password == this.inputData.password) {
              swal("Success", "User exits!", "success");
              setTimeout(function(){
                window.location.href = `/users/id/${response.data.id}/messages`;
              }, 1500);
            } else {
              swal("Opsss!", "User doesn't exits or try again", "error");
            }
          })
          .catch(error => {
            console.log(error);
            swal("Opsss!", "User doesn't exits", "error");
          });
    },
    addUser: function (){
      const url = `/api/users`;
      axios.post(url,
          {
            username: this.createAccountData.username,
            password: this.createAccountData.password,
            email : this.createAccountData.email,
            created: new Date().toISOString()
          })
          .then(response => {
            swal("Success", "Account created, enjoy your stay!", "success");
            this.hideAccountForm= true;
          })
          .catch(error => {
            console.log(error)
            swal("Opsss!", "try again please", "error");
          })
    }
  }
});
</script>

<style>
#firstHeaderGap{
  padding-top: 25px;
}
#topMargin {
  margin-top: 25px;
}
#textColor {
  color: white;
}
</style>