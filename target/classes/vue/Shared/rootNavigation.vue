<template id="rootNavigation">
  <div class="rootNavigation">
    <div class="container">

      <nav class="navbar navbar-expand-lg navbar-light">
        <p class="h2" id="alignText">iMessage</p>
      </nav>

      <div class="content mt-3">
        <div class="container-fluid">
          <slot></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
Vue.component("nav-bar", {template: "#rootNavigation"});
</script>


<style>
.navbar{
  background-color: #0C8798;
}
#alignText {
  text-align: center;
  width: 100%;
  color: white;
}
</style>
