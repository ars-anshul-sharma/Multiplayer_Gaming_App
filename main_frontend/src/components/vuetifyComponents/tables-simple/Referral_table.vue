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
                Phone Number
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(referee, index) in myReferees" :key="index">
              <td>
                <p class="text-sm font-weight-bold mb-0">
                  {{ referee.refereePhoneNumber }}
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
// import Vue from "Vue";
import axios from "axios";

export default {
  data() {
    return {
      myReferees: this.$store.getters.getMyReferees,
      phoneNumber: this.$store.getters.getRegPhoneNumber,
    };
  },
  methods: {
    async loadMyReferees() {
      axios
        .post(this.$store.getters.getBaseUrl + "/referral/load-my-referees", {
          phoneNumber: "+91" + this.phoneNumber,
        })
        .then((response) => {
          this.$store.dispatch("setMyReferees", response.data);
          this.myReferees = this.$store.getters.getMyReferees;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created() {
    this.loadMyReferees();
  },
};
</script>
