<template>
  <BaseCard heading="Credit Balance">
    <v-text-field
      v-model="phoneNumber"
      label="Enter Phone Number"
    ></v-text-field>
    <v-text-field v-model="creditAmount" label="Enter Amount"></v-text-field>
    <v-btn
      class="text-capitalize element-0"
      color="success"
      @click="creditBalance()"
      >Credit</v-btn
    >
  </BaseCard>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      phoneNumber: "",
      creditAmount: "",
    };
  },
  methods: {
    async creditBalance() {
      axios
        .post(this.$store.getters.getBaseUrl + "/user/credit-amount", {
          phoneNumber: "+91" + this.phoneNumber,
          creditAmount: this.creditAmount,
        })
        .then((response) => {
          window.Swal.fire({
            title: "Good job!",
            text: response.data["message"],
            icon: "success",
          });
        })
        .catch((error) => {
          window.Swal.fire({
            title: "Failure!",
            text: error.response.data["message"],
            icon: "error",
          });
        });
    },
    async updateRefereeCommission() {
      axios
        .post(
          this.$store.getters.getBaseUrl +
            "/referral/update-referrer-commissions",
          {
            phoneNumber: "+91" + this.myCommissionNumber,
            myCommission: this.myCommission,
          }
        )
        .then((response) => {
          window.Swal.fire({
            title: "Good job!",
            text: response.data["message"],
            icon: "success",
          });
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
