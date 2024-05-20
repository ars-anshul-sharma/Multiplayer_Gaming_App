<template>
  <BaseCard heading="Update Winner And Loser">
    <div class="mt-4">
      <v-simple-table dark>
        <template v-slot:default>
          <thead>
            <tr>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                Game Id
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Player1
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Player2
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Winning Amount
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                P1 Game Status
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                P2 Game Status
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                P1 Play Status
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                P2 Play Status
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Winner
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Update Status
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Cancel Game
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(game, index) in pendingGames" :key="index">
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.gameId }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.playerOneUsername }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.playerTwoUsername }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.winningAmount }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.player1GameStatus }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.player2GameStatus }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.player1Status }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.player2Status }}
                </p>
              </td>
              <td>
                <v-text-field v-model="finalWinner"></v-text-field>
              </td>
              <td>
                <v-btn
                  class="text-capitalize element-0"
                  color="success"
                  @click="updateFinalStatus(game)"
                >
                  Update
                </v-btn>
              </td>
              <td>
                <v-btn
                  class="text-capitalize element-0"
                  color="success"
                  @click="cancelGame(game)"
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
      myCommissionNumber: "",
      myCommission: "",
      pendingGames: [],
      finalWinner: "",
    };
  },
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
    loadPendingGames() {
      axios
        .get(this.$store.getters.getBaseUrl + "/game/pending-games")
        .then((response) => {
          this.pendingGames = response.data;
        });
    },
    updateFinalStatus(game) {
      axios
        .post(this.$store.getters.getBaseUrl + "/game/update-final-status", {
          gameId: game.gameId,
          winner: this.finalWinner,
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
    cancelGame(game) {
      axios
        .post(this.$store.getters.getBaseUrl + "/game/final-cancel", {
          gameId: game.gameId,
          playerOneUsername: game.playerOneUsername,
          player1GameStatus: game.player1GameStatus,
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
  },
  mounted() {
    setInterval(() => {
      this.loadPendingGames();
    }, 2000);
  },
};
</script>
