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
                User Name
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                Phone Number
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(user, index) in userList" :key="index">
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ user.userName }}
                </p>
              </td>
              <td>
                <p class="text-md font-weight-bold mb-2">
                  {{ user.phoneNumber }}
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
export default {
  data() {
    return {
      userList: this.$store.getters.getUserList,
    };
  },
  methods: {
    loadUserList() {},
  },
  mounted() {
    setInterval(() => {
      this.$store.dispatch("setUserList");
      this.userList = this.$store.getters.getUserList;
    }, 2000);
  },
};
</script>
