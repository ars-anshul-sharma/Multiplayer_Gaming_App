<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Register New User">
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
          <v-text-field
            v-model="userName"
            label="Enter User Name"
            v-if="userNameInputFlag"
          ></v-text-field>
          <v-text-field
            v-model="referrerCode"
            label="Enter Refer Code"
            v-if="referralFlag"
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
            v-if="registerButton"
            @click="sendOtp()"
            >Resend OTP</v-btn
          >
          <v-spacer />
          <v-btn
            class="text-capitalize element-0"
            color="success"
            v-if="registerButton"
            @click="validateOtp()"
            >Register</v-btn
          > 
          <v-btn
            class="text-capitalize element-0"
            color="success"
            v-if="userNameButtonFlag"
            @click="updateUserName()"
            >Register User</v-btn
          >
          <v-btn
            class="text-capitalize element-0"
            color="success"
            v-if="referralSkipButtonFlag"
            @click="skipRefer()"
            >Skip</v-btn
          >
          <v-btn
            class="text-capitalize element-0 ml-5"
            color="success"
            v-if="referralButtonFlag"
            @click="setRefer()"
            >Refer</v-btn
          >
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
// import Vue from "Vue";
import axios from "axios";

export default {
  data() {
    return {
      phoneNumber: "",
      otpNumber: "",
      userName: "",
      phoneNumberFlag: true,
      otpFlag: false,
      sendOtpButton: true,
      registerButton: false,
      userNameInputFlag: false,
      userNameButtonFlag: false,
      referralFlag: false,
      referralButtonFlag: false,
      referralSkipButtonFlag: false,
      refereeCode: "",
      referrerCode: "",
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
            this.registerButton = true;
          } else {
            window.Swal.fire({
              title: "Failure!",
              text: response.data.message,
              icon: "error",
            });
          }
        });
    },
    validateOtp() {
      axios
        .post(this.$store.getters.getBaseUrl + "/otp/validate-register-otp", {
          phoneNumber: "+91" + this.phoneNumber,
          otpNumber: this.otpNumber,
          userType: "USER",
        })
        .then((response) => {
          if (response.data.status == "SUCCESS") {
            window.Swal.fire({
              title: "Done!",
              text: response.data.message,
              icon: "success",
            });
            this.$store.dispatch("setRegPhoneNumber", this.phoneNumber);
            this.otpFlag = false;
            this.userNameInputFlag = true;
            this.phoneNumberFlag = false;
            this.userNameButtonFlag = true;
            this.registerButton = false;
          } else {
            window.Swal.fire({
              title: "Failure!",
              text: response.data.message,
              icon: "error",
            });
          }
        });
    },
    updateUserName() {
      axios
        .post(this.$store.getters.getBaseUrl + "/user/update", {
          phoneNumber: "+91" + this.phoneNumber,
          userName: this.userName,
        })
        .then((response) => {
          window.Swal.fire({
            title: "Good job!",
            text: response.data["message"],
            icon: "success",
          });
          this.$store.dispatch("setPlayerOneUsername", this.userName);
          (this.userNameInputFlag = false),
            (this.userNameButtonFlag = false),
            (this.referralFlag = true),
            (this.referralSkipButtonFlag = true),
            (this.referralButtonFlag = true);
          this.generateMyReferralCode();
          // Redirect to the dashboard upon successful login
          // this.$router.push("/dashboard");
          // this.$store.dispatch("setRegPhoneNumber", this.phoneNumber);
          // this.setUser();
        })
        .catch((error) => {
          window.Swal.fire({
            title: "Failure!",
            text: error.response.data["message"],
            icon: "error",
          });
        });
    },
    skipRefer() {
      this.$router.push("/dashboard");
      this.$store.dispatch("setRegPhoneNumber", this.phoneNumber);
      this.setUser();
    },
    generateMyReferralCode() {
      axios
        .post(this.$store.getters.getBaseUrl + "/referral/generate", {
          phoneNumber: "+91" + this.phoneNumber,
        })
        .then((response) => {
          this.refereeCode = response.data.refereeCode;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    setRefer() {
      axios
        .post(this.$store.getters.getBaseUrl + "/referral/set-referrer", {
          refereeCode: this.refereeCode,
          referrerCode: this.referrerCode,
        })
        .then((response) => {
          window.Swal.fire({
            title: "Good job!",
            text: response.data["message"],
            icon: "success",
          });
          this.$router.push("/dashboard");
          this.$store.dispatch("setRegPhoneNumber", this.phoneNumber);
          this.setUser();
        })
        .catch((error) => {
          window.Swal.fire({
            title: "Failure!",
            text: error.response.data["message"],
            icon: "error",
          });
        });
    },
  },
};
</script>

<style></style>
