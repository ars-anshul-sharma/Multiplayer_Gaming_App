<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Withdraw Your Balance">
          <v-sheet class="mx-auto" max-width="600">
            <v-slide-group show-arrows>
              <v-slide-group-item
                v-for="n in 25"
                :key="n"
                v-slot="{ isSelected, toggle }"
              >
                <v-btn
                  :color="isSelected ? 'primary' : undefined"
                  class="ma-2"
                  rounded
                  @click="toggle"
                >
                  Options {{ n }}
                </v-btn>
              </v-slide-group-item>
            </v-slide-group>
          </v-sheet>
          <v-text-field
            v-model="roomId"
            label="Account Holder Name"
          ></v-text-field>
          <v-text-field v-model="roomId" label="Add Your UPI"></v-text-field>
          <v-text-field v-model="roomId" label="IFSC"></v-text-field>
          <v-btn
            class="text-capitalize element-0"
            color="success"
            @click="createBattle()"
            >Withdraw Now</v-btn
          >
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>
