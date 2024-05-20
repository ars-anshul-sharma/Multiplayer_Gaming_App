import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    base_url: "http://localhost:8090",
    Sidebar_drawer: null,
    Customizer_drawer: false,
    SidebarColor: "white",
    SidebarBg: "",
    roomId: "",
    regPhoneNumber: "",
    playerOneUsername: "",
    playerTwoUsername: "",
    waitingGames: [],
    ongoingGames: [],
    userList: [],
    myBattle: {},
    isAuthenticated: false,
    withdrawlDetails: {
      paytmNumber: "",
      phonePayNumber: "",
      upiAddress: "",
      accountHolderName: "",
      bankName: "",
      accountNumber: "",
      ifscCode: "",
    },
    currentUserRole: "",
    myReferees: [],
  },
  getters: {
    getPlayerOneUsername(state) {
      return state.playerOneUsername;
    },
    getPlayerTwoUsername(state) {
      return state.playerTwoUsername;
    },
    getWaitingGames(state) {
      return state.waitingGames;
    },
    getOngoingGames(state) {
      return state.ongoingGames;
    },
    getUserList(state) {
      return state.userList;
    },
    getWithdrawlDetails(state) {
      return state.withdrawlDetails;
    },
    getRegPhoneNumber(state) {
      return state.regPhoneNumber;
    },
    getMyBattle(state) {
      return state.myBattle;
    },
    getMyReferees(state) {
      return state.myReferees;
    },
    getBaseUrl(state) {
      return state.base_url;
    },
  },
  mutations: {
    SET_SIDEBAR_DRAWER(state, newValue) {
      state.Sidebar_drawer = newValue;
    },
    SET_CUSTOMIZER_DRAWER(state, newValue) {
      state.Customizer_drawer = newValue;
    },
    SET_SIDEBAR_COLOR(state, newValue) {
      state.SidebarColor = newValue;
    },
    SET_REG_PHONE_NUMBER(state, newValue) {
      state.regPhoneNumber = newValue;
    },
    SET_PLAYER1_USERNAME(state, newValue) {
      state.playerOneUsername = newValue;
    },
    SET_PLAYER2_USERNAME(state, newValue) {
      state.playerTwoUsername = newValue;
    },
    SET_MY_BATTLE(state, newValue) {
      state.myBattle = newValue;
    },
    SET_ROOM_ID(state, newValue) {
      state.myBattle = newValue;
    },
    SET_WAITING_GAMES(state, newValue) {
      state.waitingGames = newValue;
    },
    SET_ONGOING_GAMES(state, newValue) {
      state.ongoingGames = newValue;
    },
    SET_USER_LIST(state, newValue) {
      state.userList = newValue;
    },
    SET_WITHDRAWL_DETAILS(state, newValue) {
      (state.withdrawlDetails.paytmNumber = newValue.paytmNumber),
        (state.withdrawlDetails.phonePayNumber = newValue.phonePayNumber),
        (state.withdrawlDetails.upiAddress = newValue.upiId),
        (state.withdrawlDetails.accountHolderName = newValue.name),
        (state.withdrawlDetails.bankName = newValue.bankName),
        (state.withdrawlDetails.accountNumber = newValue.accountNumber),
        (state.withdrawlDetails.ifscCode = newValue.ifscCode);
    },
    setAuthenticated(state, isAuthenticated) {
      state.isAuthenticated = isAuthenticated;
    },
    setCurrentUserRole(state, userRole) {
      state.currentUserRole = userRole;
    },
    SET_MY_REFEREES(state, newValue) {
      state.myReferees = newValue;
    },
  },
  actions: {
    async login({ commit }, { phoneNumber, otpNumber, userType }) {
      try {
        console.log(this.state.base_url);
        const response = await axios.post(
          this.state.base_url + "/otp/validate-login-otp",
          {
            phoneNumber: "+91" + phoneNumber,
            otpNumber: otpNumber,
            userType: userType,
          }
        );
        if (response.data.status === "SUCCESS") {
          commit("setAuthenticated", true);
          commit("setCurrentUserRole", userType);
          return { success: true, message: response.data.message };
        } else {
          return { success: false, message: response.data.message };
        }
      } catch (error) {
        console.error("Error validating OTP:", error);
        return {
          success: false,
          message: "An error occurred while validating OTP.",
        };
      }
    },
    logout({ commit }) {
      commit("setAuthenticated", false);
    },
    setRegPhoneNumber({ commit, state }, newValue) {
      commit("SET_REG_PHONE_NUMBER", newValue);
      return state.regPhoneNumber;
    },
    setPlayerOneUsername({ commit, state }, newValue) {
      commit("SET_PLAYER1_USERNAME", newValue);
      return state.playerOneUsername;
    },
    setPlayerTwoUsername({ commit, state }, newValue) {
      commit("SET_PLAYER2_USERNAME", newValue);
      return state.playerTwoUsername;
    },
    setMyBattle({ commit, state }, newValue) {
      axios
        .post(this.state.base_url + "/game/my-battle", {
          playerOneUsername: newValue.playerOneUsername,
          playerTwoUsername: newValue.playerTwoUsername,
        })
        .then((response) => {
          // var a = [];
          // a.push(response.data);
          commit("SET_MY_BATTLE", response.data);
        })
        .catch((error) => {
          console.log(error.response);
        });
      return state.myBattle;
    },
    setRoomId({ commit, state }, newValue) {
      console.log(newValue);
      axios
        .post(this.state.base_url + "/game/set-room-id", {
          playerOneUsername: state.playerOneUsername,
          roomId: newValue,
        })
        .then((response) => {
          commit("SET_ROOM_ID", response.data);
          window.Swal.fire({
            title: response.data.message,
            icon: "success",
          });
        })
        .catch((error) => {
          console.log(error.response);
          window.Swal.fire({
            title: error.response.data["message"],
            icon: "error",
          });
        });
      return state.myBattle;
    },
    setWaitingGames({ commit, state }) {
      axios
        .post(this.state.base_url + "/game/waiting-games", {
          playerOneUsername: state.playerOneUsername,
        })
        .then((response) => {
          commit("SET_WAITING_GAMES", response.data);
        });
      return state.waitingGames;
    },
    setOngoingGames({ commit, state }) {
      axios
        .get(this.state.base_url + "/game/ongoing-games")
        .then((response) => {
          commit("SET_ONGOING_GAMES", response.data);
        });
      return state.ongoingGames;
    },
    setUserList({ commit, state }) {
      axios.get(this.state.base_url + "/getUserList").then((response) => {
        commit("SET_USER_LIST", response.data);
      });
      return state.userList;
    },
    setWithdrawlDetails({ commit, state }, newValue) {
      commit("SET_WITHDRAWL_DETAILS", newValue);
      return state.withdrawlDetails;
    },
    setMyReferees({ commit, state }, newValue) {
      commit("SET_MY_REFEREES", newValue);
      return state.myReferees;
    },
  },
  plugins: [createPersistedState()],
});
