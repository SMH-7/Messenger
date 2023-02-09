<template id="messenger-view">
  <nav-bar>

    <div class="card bg-light mb-3">
      <div class="card-header" id ="configColor">
        <div class="row">
          <div class="col-6">Profile</div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="signOut()">Sign out</button>
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateUser()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteUser()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
    </div>


    <div class="card-body" id="configColor">
      <form>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-user-id">User Name</span>
          </div>
          <input type="text" class="form-control" v-model="user.username" name="username" readonly/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-user-name">Email</span>
          </div>
          <input type="text" class="form-control" v-model="user.email" name="email" readonly/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" >Password</span>
          </div>
          <input type="password" class="form-control" v-model="user.password" name="password" />
        </div>
      </form>
    </div>


    <div class="card-footer text-left" id="configColor topMargin">
      <div class="col" align="right">
        <button rel="tooltip" title="Add"
                class="btn btn-info btn-simple btn-link"
                @click="hideSendForm =!hideSendForm">
          <i class="fa fa-plus" aria-hidden="true"></i>
        </button>
      </div>

      <div class="card-body" :class="{ 'd-none': hideSendForm}" id="configColor">
        <form id="addMessage">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" >Text</span>
            </div>
            <input type="text" class="form-control" v-model="message" name="text" placeholder="Message"/>
          </div>
        </form>
        <button rel="tooltip" title="Update"
                class="btn btn-info btn-simple btn-link"
                @click="addMessage()">Send</button>
      </div>


      <div class="list-group list-group-flush">
        <div class="list-group-item d-flex align-items-start" id="configColor"
             v-for="(message,index) in usermessages" v-bind:key="index">
          <div class="mr-auto p-2">
            <span><a> {{message.text}} on {{message.created.substring(0, 10)}} at {{message.created.substring(11, 16)}}</a></span>
          </div>
          <div class="p2">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="editMessage(message.id)">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteMessage(message.id, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>

        <div class="card-body" :class="{ 'd-none': hideEditForm}">

          <form id="updateMessage">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" >Text</span>
              </div>
              <input type="text" class="form-control" v-model="currMessage.text" name="text" />
            </div>
          </form>

          <button rel="tooltip" title="Update"
                  class="btn btn-info btn-simple btn-link"
                  @click="updateMessage(currMessage.id)">Done </button>
        </div>
      </div>
    </div>

  </nav-bar>
</template>

<script>
Vue.component("messenger-view",{
  template: "#messenger-view",
  data: () => ({
    user: null,
    usermessages: [],
    hideSendForm : true,
    hideEditForm : true,
    message : null,
    currMessage : [],
  }),
  created: function () {
    const userId = this.$javalin.pathParams["user-id"]
    const url = `/api/users/id/${userId}`
    const msgUrl = url + `/messages`
    axios.get(url)
        .then(res => this.user = res.data)
        .catch(error => {
          console.log(error)
        })
    axios.get(msgUrl)
        .then(res => this.usermessages = res.data)
        .catch(error => {
          swal("Opss", "No messages available", "error");
        })
  },
  methods: {
    updateUser: function () {
      const userId = this.$javalin.pathParams["user-id"];
      const url = `/api/users/name/${this.user.username}`;
      axios.patch(url,
          {
            id: this.user.id,
            username: this.user.username,
            password: this.user.password,
            email : this.user.email,
            created: new Date().toISOString()
          })
          .then( _  =>
              swal("Success", "Updated!", "success")
          .catch(error => {
            swal("Opss", "try again", "error")
          })
      )
    },
    deleteUser: function () {
      if (confirm("Do you really want to delete?")) {
        const url = `/api/users/mail/${this.user.email}`
        axios.delete(url)
            .then(response => {
              swal("Success", "Deleted!", "success")
              window.location.href = '/';
            })
            .catch(function (error) {
              swal("Opsss", "Try again!", "error")
            });
      }
    },
    addMessage: function () {
      const url = `/api/messages`;
      axios.post(url,
          {
            text: this.message,
            userId: this.user.id,
            created: new Date().toISOString()
          })
          .then(response => {
            this.usermessages.push(response.data)
            this.hideSendForm = true
            this.message = null
          })
    },
    deleteMessage: function (id, index) {
      if (confirm('Are you sure you want to delete this message? This action cannot be undone.', 'Warning')) {
        const url = `/api/messages/${id}`;
        axios.delete(url)
            .then(response =>
                this.usermessages.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    editMessage : function (id) {
      this.hideEditForm = false
      const url = `/api/messages/${id}`;
      axios.get(url)
          .then(res => {
            this.currMessage = res.data
          })
          .catch(error => {
            swal("Opss", "Servers are down, try again!", "error")
          })

    },

    updateMessage: function (id) {
      const url = `/api/messages/${id}`;
      axios.patch(url,
          {
            text: this.currMessage.text,
            userId: this.user.id,
            created: new Date().toISOString()
          })
          .then(_ =>
              this.refresh()
          ).catch(function (error) {
        swal("Opss", "Servers are down, try again!", "error")
      })
      this.hideEditForm = !this.hideEditForm
    },

    refresh: function () {
      const url = `/api/users/id/${this.user.id}`
      axios.get(url + `/messages`)
          .then(res => this.usermessages = res.data)
    },
    signOut : function (){
      window.location.href = '/'
    }
  }
});
</script>

<style>
#configColor {
  background-color: #52D5C0;
}
#topMargin {
  margin-top: 25px;
}
</style>
