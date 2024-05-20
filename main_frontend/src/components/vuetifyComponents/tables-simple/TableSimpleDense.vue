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
                You
              </th>
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
            </tr>
          </thead>
          <tbody>
            <tr v-for="(game, index) in ongoingGames" :key="index">
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
export default {
  data() {
    return {
      ongoingGames: this.$store.getters.getOngoingGames,
    };
  },
  methods: {
    loadOngoingGames() {},
  },
  mounted() {
    setInterval(() => {
      this.$store.dispatch("setOngoingGames");
      this.ongoingGames = this.$store.getters.getOngoingGames;
    }, 2000);
  },
};
</script>
