<template>
  <BaseCard heading="Debit Balance">
    <v-text-field
      v-model="phoneNumber"
      label="Enter Phone Number"
    ></v-text-field>
    <v-text-field v-model="debitAmount" label="Enter Amount"></v-text-field>
    <v-btn
      class="text-capitalize element-0"
      color="success"
      @click="debitBalance()"
      >Debit</v-btn
    >
  </BaseCard>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      phoneNumber: "",
      debitAmount: "",
    };
  },
  methods: {
    async debitBalance() {
      axios
        .post(this.$store.getters.getBaseUrl + "/user/debit-amount", {
          phoneNumber: "+91" + this.phoneNumber,
          debitAmount: this.debitAmount,
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
