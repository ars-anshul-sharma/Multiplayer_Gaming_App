<template>
  <BaseCard heading="Search And Download Image">
    <v-container>
      <v-row>
        <v-text-field
          v-model="imageName"
          label="Enter GameId And PlayerName"
        ></v-text-field>
        <v-btn color="success" @click="searchImage">Search Image</v-btn>
      </v-row>
      <v-row v-if="imageUrl" justify="center">
        <v-col cols="12" sm="8">
          <img
            :src="imageUrl"
            alt="Image"
            v-if="showImage"
            class="mobile-friendly-image"
          />
        </v-col>
      </v-row>
    </v-container>
  </BaseCard>
</template>

<script>
export default {
  data() {
    return {
      imageName: "",
      imageUrl: "",
      showImage: false,
    };
  },
  methods: {
    async searchImage() {
      try {
        const response = await fetch(
          this.$store.getters.getBaseUrl + `/game/image?name=${this.imageName}`
        );
        if (response.ok) {
          const imageData = await response.blob();
          this.imageUrl = URL.createObjectURL(imageData);
          this.showImage = true;
        } else {
          console.error("Failed to fetch image:", response.statusText);
          this.reset();
        }
      } catch (error) {
        console.error("Error fetching image:", error);
        this.reset();
      }
    },
    reset() {
      this.imageUrl = "";
      this.showImage = false;
    },
  },
};
</script>

<style scoped>
.mobile-friendly-image {
  max-width: 90%; /* Adjust the max-width to make the image slightly bigger */
  height: auto; /* Maintain the aspect ratio of the image */
  display: block; /* Ensure the image does not create extra space below */
}
</style>
