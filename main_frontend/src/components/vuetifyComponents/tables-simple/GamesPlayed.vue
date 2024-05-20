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
                Battle Amount
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Winning Amount
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                Status
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(game, index) in gamePlayed" :key="index">
              <td>
                <p class="text-sm font-weight-bold mb-0">
                  <v-card v-if="playerOneUsername == game.playerOneUsername">{{
                    game.playerTwoUsername
                  }}</v-card>

                  <v-card v-else>
                    {{ game.playerOneUsername }}
                  </v-card>
                </p>
              </td>
              <td>
                <p class="text-sm font-weight-bold mb-0">
                  {{ game.bettingAmount }}
                </p>
              </td>
              <td>
                <span class="text-xs font-weight-bold">
                  {{ game.winningAmount }}
                </span>
              </td>
              <td>
                <v-card v-if="playerOneUsername == game.playerOneUsername">{{
                  game.player1Status
                }}</v-card>

                <v-card v-else>
                  {{ game.player2Status }}
                </v-card>
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
      playerOneUsername: this.$store.getters.getPlayerOneUsername,
      gamePlayed: [],
    };
  },
  methods: {
    loadGamesPlayed() {
      axios
        .post(this.$store.getters.getBaseUrl + "/game/played", {
          playerOneUsername: this.playerOneUsername,
        })
        .then((response) => {
          this.gamePlayed = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created() {
    this.loadGamesPlayed();
  },
};
</script>
