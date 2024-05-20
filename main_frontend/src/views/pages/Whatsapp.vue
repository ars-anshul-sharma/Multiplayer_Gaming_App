<template>
    <div>
      <!-- Floating WhatsApp button -->
      <!-- Floating WhatsApp button -->
    <a
      :href="whatsappLink"
      class="whatsapp-button"
      target="_blank"
      rel="noopener noreferrer"
    >
      <v-btn color="green" dark fab>
        <v-icon>mdi-whatsapp</v-icon>
      </v-btn>
    </a>
    </div>
  </template>
  
  <script>
  
  export default {
    data() {
      return {
        phoneNumber: '918005911008', // Replace with your desired WhatsApp number
      };
    },
    computed: {
      whatsappLink() {
        return `https://wa.me/${this.phoneNumber}`;
      },
    },
  }
  </script>
  
  <style scoped>
  .whatsapp-button {
    position: fixed;
    bottom: 64px;
    right: 16px;
    z-index: 1000; /* Ensure the button appears above other elements */
    text-decoration: none; /* Remove underline for anchor */
  }
  </style>
  