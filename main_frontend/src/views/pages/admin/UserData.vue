<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Games Played">
          <UserList></UserList>
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

import UserList from "../../../components/vuetifyComponents/tables-simple/UserList.vue" 
export default {
  data() {
    return {};
  },
  components: {
    UserList,
 },
};
</script>
