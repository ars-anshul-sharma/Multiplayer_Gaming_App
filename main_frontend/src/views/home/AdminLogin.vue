<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Admin Login">
          <v-text-field
            v-model="phoneNumber"
            label="Enter Phone Number"
            v-if="phoneNumberFlag"
          ></v-text-field>
          <v-text-field
            v-model="otpNumber"
            label="Enter OTP"
            v-if="otpFlag"
          ></v-text-field>
          <v-btn
            class="text-capitalize element-0"
            color="success"
            v-if="sendOtpButton"
            @click="sendOtp()"
            >Send OTP</v-btn
          >
          <v-btn
            class="text-capitalize element-0"
            color="success"
            v-if="loginButton"
            @click="validateOtp()"
            >Login</v-btn
          >
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
// import Vue from "Vue";
import axios from "axios";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      phoneNumber: "",
      otpNumber: "",
      userName: "",
      phoneNumberFlag: true,
      otpFlag: false,
      sendOtpButton: true,
      loginButton: false,
    };
  },

  methods: {
    sendOtp() {
      axios
        .post(this.$store.getters.getBaseUrl + "/otp/send-otp", {
          phoneNumber: "+91" + this.phoneNumber,
        })
        .then((response) => {
          if (response.data.status == "DELIVERED") {
            window.Swal.fire({
              title: "Otp Sent!",
              icon: "success",
            });
            this.otpFlag = true;
            this.sendOtpButton = false;
            this.loginButton = true;
          } else {
            window.Swal.fire({
              title: "Failure!",
              text: response.data.message,
              icon: "error",
            });
          }
        });
    },
    setUser() {
      axios
        .post(this.$store.getters.getBaseUrl + "/user/get", {
          phoneNumber: "+91" + this.phoneNumber,
        })
        .then((response) => {
          this.$store.dispatch("setPlayerOneUsername", response.data.userName);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    ...mapActions(["login"]),
    async validateOtp() {
      const { success, message } = await this.login({
        phoneNumber: this.phoneNumber,
        otpNumber: this.otpNumber,
        userType: "ADMIN",
      });
      if (success) {
        window.Swal.fire({
          title: "Done!",
          text: message,
          icon: "success",
        });
        this.$router.push("/admin/admin-dashboard");
        this.$store.dispatch("setRegPhoneNumber", this.phoneNumber);
        this.setUser();
      } else {
        window.Swal.fire({
          title: "Failure!",
          text: message,
          icon: "error",
        });
      }
    },
  },
};
</script>
