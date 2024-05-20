// store/auth.js
const state = {
    isAuthenticated: false,
  };
  
  const mutations = {
    setAuthentication(state, isAuthenticated) {
      state.isAuthenticated = isAuthenticated;
    },
  };
  
  const actions = {
    setAuthentication({ commit }, isAuthenticated) {
      commit("setAuthentication", isAuthenticated);
    },
  };
  
  const getters = {
    isAuthenticated: (state) => state.isAuthenticated,
  };
  
  export default {
    state,
    mutations,
    actions,
    getters,
  };
  