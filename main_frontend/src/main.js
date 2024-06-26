import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router";
import store from "./store";
import "./plugins/base";

Vue.config.productionTip = false;

new Vue({
  vuetify,
  store,
  router,
  render: (h) => h(App),
}).$mount("#app");

Vue.prototype.$base_url = "http://localhost:8090";

// router.beforeEach(() => {
//   console.log("Anshul");
// });
