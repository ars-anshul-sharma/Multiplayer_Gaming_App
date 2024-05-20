<template>
  <BaseCard heading="Withdrawl Requests">
    <div class="mt-4">
      <v-simple-table dark>
        <template v-slot:default>
          <thead>
            <tr>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                Withdrawl Info
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Amount
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Status
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Approve
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Cancel
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(request, index) in withdrawlRequests" :key="index">
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ request.withdrawDetails.regPhoneNumber.substring(3) }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ request.withdrawlAmount }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ request.status }}
                </p>
              </td>
              <td>
                <v-btn
                  class="text-capitalize element-0"
                  color="success"
                  @click="approveRequest(request)"
                >
                  Approve
                </v-btn>
              </td>
              <td>
                <v-btn
                  class="text-capitalize element-0"
                  color="success"
                  @click="cancelRequest(request)"
                >
                  Cancel
                </v-btn>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>
  </BaseCard>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      withdrawlRequests: [],
    };
  },
  methods: {
    loadWithdrawlRequests() {
      axios
        .get(this.$store.getters.getBaseUrl + "/user/withdrawl-requests")
        .then((response) => {
          this.withdrawlRequests = response.data;
          console.log(response.data);
        });
    },
    approveRequest(request) {
      axios
        .post(
          this.$store.getters.getBaseUrl + "/user/approve-withdrawl-request",
          {
            reqId: request.reqId,
          }
        )
        .then((response) => {
          window.Swal.fire({
            text: response.data["message"],
            icon: "success",
          });
        })
        .catch((error) => {
          window.Swal.fire({
            text: error.response.data["message"],
            icon: "error",
          });
        });
    },
    cancelRequest(request) {
      axios
        .post(
          this.$store.getters.getBaseUrl + "/user/cancel-withdrawl-request",
          {
            reqId: request.reqId,
          }
        )
        .then((response) => {
          window.Swal.fire({
            text: response.data["message"],
            icon: "success",
          });
        })
        .catch((error) => {
          window.Swal.fire({
            text: error.response.data["message"],
            icon: "error",
          });
        });
    },
  },
  mounted() {
    setInterval(() => {
      this.loadWithdrawlRequests();
    }, 2000);
  },
};
</script>
