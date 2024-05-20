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
                Opponent
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Entry Fee
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Winning Amount
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Play Battle
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(game, index) in waitingGames" :key="index">
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.playerOneUsername }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.bettingAmount }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ game.winningAmount }}
                </p>
              </td>
              <td>
                <v-btn
                  class="text-capitalize element-0"
                  color="success"
                  @click="joinBattle(game)"
                >
                  Play
                </v-btn>
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
      waitingGames: this.$store.getters.getWaitingGames,
      roomIdDialog: false,
      roomId: "",
    };
  },
  methods: {
    joinBattle(game) {
      axios
        .post(this.$store.getters.getBaseUrl + "/game/join", {
          gameId: game.gameId,
          playerOneUsername: game.playerOneUsername,
          playerTwoUsername: this.playerOneUsername,
          gameStatus: "Waiting",
        })
        .then((response) => {
          console.log(response);
          window.Swal.fire({
            title: "Game Joined",
            icon: "success",
          });
          this.$store.dispatch("setMyBattle", game);
          this.$router.push("/dashboard/set_game");
        })
        .catch((error) => {
          console.log(error.response);
          window.Swal.fire({
            title: error.response.data["message"],
            icon: "error",
          });
        });
    },
    generateRoomId() {
      window.location.href = "https://ludoking.com/";
    },
  },
  mounted() {
    setInterval(() => {
      this.$store.dispatch("setWaitingGames");
      this.waitingGames = this.$store.getters.getWaitingGames;
    }, 2000);
  },
};
</script>
