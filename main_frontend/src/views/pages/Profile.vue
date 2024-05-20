<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" lg="4">
        <v-card>
          <v-card-text class="text-center pa-7">
            <img
              src="https://media.gettyimages.com/id/164545348/vector/baseball-player-cute-kid-cartoon-illustration.jpg?s=612x612&w=0&k=20&c=CKvtBn3VxCYPHWJIGfhC46G_cZJ79xZ8twsQFSE9GJ8="
              alt="user"
              width="150px"
              class="img-fluid rounded-circle shadow-sm"
            />
            <h4
              class="mt-2 title blue-grey--text text--darken-2 font-weight-regular"
            >
              {{ playerOneUsername }}
            </h4>
            <h6 class="subtitle-2 font-weight-light">
              {{ regPhoneNumber }}
            </h6>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Save Withdrawal Details">
          <v-text-field
            v-model="withdrawlDetails.paytmNumber"
            label="Paytm Wallet Number"
          ></v-text-field>
          <v-text-field
            v-model="withdrawlDetails.phonePayNumber"
            label="PhonePay Number"
          ></v-text-field>
          <v-text-field
            v-model="withdrawlDetails.upiAddress"
            label="Add Your UPI"
          ></v-text-field>
          <v-text-field
            v-model="withdrawlDetails.accountHolderName"
            label="Account Holder Name"
          ></v-text-field>
          <v-text-field
            v-model="withdrawlDetails.bankName"
            label="Bank Name"
          ></v-text-field>
          <v-text-field
            v-model="withdrawlDetails.accountNumber"
            label="Account Number"
          ></v-text-field>
          <v-text-field
            v-model="withdrawlDetails.ifscCode"
            label="IFSC"
          ></v-text-field>
          <v-btn
            class="text-capitalize element-0"
            color="success"
            @click="saveWithdrawlDetails()"
            >Save Details</v-btn
          >
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      playerOneUsername: this.$store.getters.getPlayerOneUsername,
      regPhoneNumber: this.$store.getters.getRegPhoneNumber,
      withdrawlDetails: this.$store.getters.getWithdrawlDetails,
    };
  },
  methods: {
    async saveWithdrawlDetails() {
      axios.post(
        this.$store.getters.getBaseUrl + "/user/save-withdrawl-details",
        {
          regPhoneNumber: "+91" + this.regPhoneNumber,
          paytmNumber: this.withdrawlDetails.paytmNumber,
          phonePayNumber: this.withdrawlDetails.phonePayNumber,
          upiId: this.withdrawlDetails.upiAddress,
          name: this.withdrawlDetails.accountHolderName,
          bankName: this.withdrawlDetails.bankName,
          accountNumber: this.withdrawlDetails.accountNumber,
          ifscCode: this.withdrawlDetails.ifscCode,
        }
      );
    },
    async getWithdrawlDetails() {
      axios
        .post(this.$store.getters.getBaseUrl + "/user/get-withdrawl-details", {
          regPhoneNumber: "+91" + this.regPhoneNumber,
        })
        .then((response) => {
          this.$store.dispatch("setWithdrawlDetails", response.data);
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    },
  },
  mounted() {
    this.getWithdrawlDetails();
  },
};
</script>
