<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Add Balance">
          <v-text-field v-model="amount" label="Amount"></v-text-field>
          <v-btn
            class="text-capitalize element-0"
            color="success"
            @click="createBattle()"
            >Process</v-btn
          >
        </BaseCard>
        <BaseCard heading="Redeem Your Refer Balance">
          <v-text-field
            v-model="amount"
            label="Enter Amount (Min: 95, Max: 10000)"
          ></v-text-field>
          <v-btn
            class="text-capitalize element-0"
            color="success"
            @click="createBattle()"
            >Redeem</v-btn
          >
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>
