<template>
  <div>
    <div class="mt-4">
      <v-simple-table dark>
        <template v-slot:default>
          <thead>
            <tr>
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
                Opponent
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Play Battle
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ myBattle.bettingAmount }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ myBattle.winningAmount }}
                </p>
              </td>
              <td class="text-md font-weight-bold mb-2">
                <section v-if="playerOneUsername == myBattle.playerTwoUsername">
                  {{ myBattle.playerOneUsername }}
                </section>
                <section v-if="playerOneUsername != myBattle.playerTwoUsername">
                  {{ myBattle.playerTwoUsername }}
                </section>
              </td>
              <td>
                <v-btn
                  v-if="myBattle.playerTwoUsername != null"
                  class="text-capitalize element-0"
                  color="success"
                  @click="playGame()"
                >
                  Play
                </v-btn>
                <v-btn
                  v-if="
                    myBattle.playerTwoUsername == null &&
                      myBattle.playerOneUsername != null
                  "
                  class="text-capitalize element-0"
                  color="warning"
                  @click="
                    cancelGame(myBattle.gameId, myBattle.playerOneUsername)
                  "
                >
                  Cancel
                </v-btn>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>

    <!-- Room ID Dialog -->
    <!-- <v-dialog v-model="roomIdDialog" max-width="500">
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
    </v-dialog> -->
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      playerOneUsername: this.$store.getters.getPlayerOneUsername,
      myBattle: this.$store.getters.getMyBattle,
      roomIdDialog: false,
      roomId: "",
    };
  },
  methods: {
    cancelGame(id, userName) {
      axios
        .post(this.$store.getters.getBaseUrl + "/game/cancel-my-game", {
          gameId: id,
          playerOneUsername: userName,
          gameStatus: "Waiting",
        })
        .then((response) => {
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
    playGame() {
      this.$store.dispatch("setMyBattle", {
        playerOneUsername: this.playerOneUsername,
      });
      this.$router.push("/dashboard/set_game");
    },
  },
  mounted() {
    setInterval(() => {
      this.$store.dispatch("setMyBattle", {
        playerOneUsername: this.playerOneUsername,
      });
      this.myBattle = this.$store.getters.getMyBattle;
    }, 2000);
  },
};
</script>
