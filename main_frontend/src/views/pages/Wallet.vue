<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Add Balance">
          <v-text-field v-model="addAmount" label="Amount"></v-text-field>
          <v-btn
            class="text-capitalize element-0"
            color="success"
            @click="paymentStart()"
            >Add</v-btn
          >
        </BaseCard>
        <BaseCard heading="Withdraw Amount">
          <div>
            <v-tabs v-model="selectedTab">
              <v-tab
                v-for="(tab, index) in tabs"
                :key="index"
                @click="selectedTab = index"
              >
                {{ tab }}
              </v-tab>
            </v-tabs>

            <v-card v-if="selectedTab === 0">
              <div>
                <v-text-field
                  v-model="withdrawlDetails.accountHolderName"
                  label="Account Holder Name"
                ></v-text-field>
                <v-text-field
                  v-model="withdrawlDetails.upiAddress"
                  label="UPI Address"
                ></v-text-field>
              </div>
            </v-card>

            <v-card v-if="selectedTab === 1">
              <div>
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
                  label="IFSC Code"
                ></v-text-field>
              </div>
            </v-card>
          </div>
          <v-text-field
            v-model="withdrawAmount"
            label="Enter Amount (Min: 100, Max: 10000)"
          ></v-text-field>
          <v-btn
            class="text-capitalize element-0"
            color="success"
            @click="createWithdrawRequest()"
            >Withdraw</v-btn
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
      addAmount: "",
      withdrawAmount: "",
      phoneNumber: this.$store.getters.getRegPhoneNumber,
      withdrawlDetails: this.$store.getters.getWithdrawlDetails,
      selectedTab: 0,
      tabs: ["UPI", "Direct Bank"],
    };
  },
  methods: {
    upiRequest() {
      axios
        .post(this.$store.getters.getBaseUrl + "/payment/upi-withdrawl", {
          withdrawAmount: this.withdrawAmount,
          phoneNumber: "+91" + this.phoneNumber,
          upiAddress: this.withdrawlDetails.upiAddress,
          accountHolderName: this.withdrawlDetails.accountHolderName,
        })
        .then((response) => {
          console.log(response);
          window.Swal.fire({
            title: response.data.message,
            icon: "success",
          });
        })
        .catch((error) => {
          window.Swal.fire({
            title: error.response.data["message"],
            icon: "error",
          });
        });
    },
    bankRequest() {
      axios
        .post(this.$store.getters.getBaseUrl + "/payment/bank-withdrawl", {
          withdrawAmount: this.withdrawAmount,
          phoneNumber: "+91" + this.phoneNumber,
          ifscCode: this.withdrawlDetails.ifscCode,
          accountHolderName: this.withdrawlDetails.accountHolderName,
          bankName: this.withdrawlDetails.bankName,
          accountNumber: this.withdrawlDetails.accountNumber,
        })
        .then((response) => {
          console.log(response);
          window.Swal.fire({
            title: response.data.message,
            icon: "success",
          });
        })
        .catch((error) => {
          window.Swal.fire({
            title: error.response.data["message"],
            icon: "error",
          });
        });
    },
    createWithdrawRequest() {
      if (this.selectedTab == 0) {
        this.upiRequest();
      } else {
        this.bankRequest();
      }
    },
    paymentStart() {
      axios
        .post(this.$store.getters.getBaseUrl + "/payment/create-order", {
          amount: this.addAmount,
          phoneNumber: "+91" + this.$store.getters.getRegPhoneNumber,
        })
        .then((response) => {
          console.log(response);
          if (response.data.status == "created") {
            let self = this;
            let options = {
              key: "rzp_test_VEca5dqOUnQdVj",
              amount: response.data.amount,
              currency: "INR",
              name: "Game Recharge",
              description: "Add money to play games",
              image: "",
              order_id: response.data.id,
              handler: function(response) {
                // console.log(response.razorpay_payment_id);
                // console.log(response.razorpay_order_id);
                // console.log(response.razorpay_signature);

                self.updatePaymentOnServer(
                  response.razorpay_payment_id,
                  response.razorpay_order_id,
                  "paid"
                );
              },
              prefill: {
                name: "",
                email: "",
                contact: "",
              },
              notes: {
                address: "Please Games",
              },
              theme: {
                color: "#3399cc",
              },
            };
            let rzp = new window.Razorpay(options);
            rzp.on("payment.failed", function(response) {
              // console.log(response.error.code);
              // console.log(response.error.description);
              // console.log(response.error.source);
              // console.log(response.error.step);
              // console.log(response.error.reason);
              // console.log(response.error.metadata.order_id);
              // console.log(response.error.metadata.payment_id);
              self.updatePaymentOnServer(
                response.razorpay_payment_id,
                response.razorpay_order_id,
                "failed"
              );
            });
            rzp.open();
          }
        })
        .catch((error) => {
          console.log(error.response);
          alert("Something went wrong!!");
        });
    },
    updatePaymentOnServer(payment_id, order_id, status) {
      axios
        .post(this.$store.getters.getBaseUrl + "/payment/update-order", {
          payment_id: payment_id,
          order_id: order_id,
          status: status,
        })
        .then((response) => {
          console.log(response);
          window.Swal.fire({
            title: "Good job!",
            text: "Payment Successful!",
            icon: "success",
          });
        })
        .catch((error) => {
          console.log(error);
          window.Swal.fire({
            title: "Failure!",
            text: "Oops Payment Failed!",
            icon: "error",
          });
        });
    },
  },
};
</script>
