<template>
  <div>
    <div class="mt-4">
      <v-simple-table dark>
        <template v-slot:default>
          <thead>
            <tr>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                Time
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
            </tr>
          </thead>
          <tbody>
            <tr v-for="(each, index) in transactions" :key="index">
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ each.time }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ each.amount / 100 }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  <span v-if="each.status == 'paid'">Success</span>
                  <span v-if="each.status == 'created'">Failure</span>
                </p>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>

    <!-- Room ID Dialog -->
    <v-dialog v-model="roomIdDialog" max-width="500">
      <v-card>
        <v-card-title class="headline">Enter Room ID</v-card-title>
        <v-card-text>
          <v-text-field v-model="roomId" label="Room ID"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="roomIdDialog = false">
            Cancel
          </v-btn>
          <v-btn color="blue darken-1" @click="generateRoomId">
            Generate Room ID
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      playerOneUsername: this.$store.getters.getPlayerOneUsername,
      phoneNumber: this.$store.getters.getRegPhoneNumber,
      transactions: [],
    };
  },
  methods: {
    getTransactions() {
      axios
        .post(this.$store.getters.getBaseUrl + "/payment/transactions", {
          phoneNumber: "+91" + this.phoneNumber,
        })
        .then((response) => {
          this.transactions = response.data;
        })
        .catch((error) => {
          console.log(error.response);
        });
    },
  },
  created() {
    this.getTransactions();
  },
};
</script>
