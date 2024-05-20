<template>
  <v-app-bar app clipped-left clipped-right color="primary" dark>
    <v-avatar :tile="true">
      <img src="../../assets/Logos/Ludo_logo.jpeg" alt="logo">
    </v-avatar>
    <v-toolbar-title class="align-center d-flex">
      <span class="logo-text ml-2">
        <!-- <img src="../../assets/Logos/Ludo_text.png" class="mt-2" /> -->
        Karni Ludo Club
      </span>
    </v-toolbar-title>
    <v-spacer />
    <v-spacer />
    <v-spacer />
    <v-spacer />
    <v-spacer />
    <v-btn class="text-capitalize element-0" color="success" href="#/home/login">Login</v-btn><v-spacer />
    <v-btn class="text-capitalize element-0" color="success" href="#/home/register">Register</v-btn>
  </v-app-bar>
</template>
