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
                User
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
                  {{ each.userName }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ each.amount / 100 }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  <span v-if="each.status == 'paid'">Credit</span>
                  <span v-if="each.status == 'created'">Failure</span>
                  <span v-if="each.status == 'debit'">Debit</span>
                </p>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: {
    apiEndpoint: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      transactions: [],
    };
  },
  methods: {
    getTransactions() {
      axios
        .post(this.apiEndpoint)
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
