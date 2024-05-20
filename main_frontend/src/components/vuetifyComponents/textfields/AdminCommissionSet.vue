<template>
  <BaseCard heading="Set Commission">
    <v-text-field
      v-model="myCommissionNumber"
      label="Enter Phone Number"
    ></v-text-field>
    <v-text-field
      v-model="myCommission"
      label="Enter commission %"
    ></v-text-field>
    <v-btn
      class="text-capitalize element-0"
      color="success"
      @click="setCommission()"
      >Set</v-btn
    >
    &nbsp;
    <v-btn
      class="text-capitalize element-0"
      color="success"
      @click="updateRefereeCommission()"
      >Update Referees</v-btn
    >
  </BaseCard>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      myCommissionNumber: "",
      myCommission: "",
    };
  },
  created() {},
  methods: {
    async setCommission() {
      axios
        .post(this.$store.getters.getBaseUrl + "/referral/set-mycommission", {
          phoneNumber: "+91" + this.myCommissionNumber,
          myCommission: this.myCommission,
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
