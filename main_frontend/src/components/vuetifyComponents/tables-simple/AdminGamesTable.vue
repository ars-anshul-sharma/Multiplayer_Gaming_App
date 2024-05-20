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
                Player 1
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                Player 2
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
            <tr v-for="(game, index) in games" :key="index">
              <td>
                <div class="d-flex px-2">
                  <div class="my-auto">
                    <h6 class="mb-0 text-sm">
                      {{ game.playerOneUsername }}
                    </h6>
                  </div>
                </div>
              </td>
              <td>
                <div class="d-flex px-2">
                  <div class="my-auto">
                    <h6 class="mb-0 text-sm">
                      {{ game.playerTwoUsername }}
                    </h6>
                  </div>
                </div>
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
      games: [],
    };
  },
  methods: {
    loadGames() {
      axios.get(this.apiEndpoint)
        .then((response) => {
          this.games = response.data;
        })
        .catch((error) => {
          console.error('Error fetching games:', error);
        });
    },
  },
  mounted() {
    // Load games initially
    this.loadGames();

    // Fetch games periodically
    setInterval(() => {
      this.loadGames();
    }, 1000);
  },
};
</script>
