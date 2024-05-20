<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <div class="player-card">
          <div class="player">
            <h3 v-if="myBattle.playerOneUsername == playerOneUsername">
              {{ myBattle.playerOneUsername }}
            </h3>
            <h3 v-if="myBattle.playerTwoUsername == playerOneUsername">
              {{ myBattle.playerTwoUsername }}
            </h3>
          </div>
          <div class="vs">vs</div>
          <div class="player">
            <h3 v-if="myBattle.playerTwoUsername != playerOneUsername">
              {{ myBattle.playerTwoUsername }}
            </h3>
            <h3 v-if="myBattle.playerOneUsername != playerOneUsername">
              {{ myBattle.playerOneUsername }}
            </h3>
          </div>
        </div>
        <div class="player-card">
          <p>Winning Amount: {{ myBattle.winningAmount }}</p>
        </div>
        <v-card v-if="myBattle.playerOneUsername != playerOneUsername">
          <v-card-title>Room ID: {{ roomId }}</v-card-title>
        </v-card>
        <v-card v-else>
          <BaseCard heading="Set Room Code">
            <v-text-field v-model="roomId" label="Room Code"></v-text-field>
            <v-btn
              class="text-capitalize element-0"
              color="success"
              @click="setRoomId()"
              >Set Now</v-btn
            >
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <v-btn
              class="text-capitalize element-0"
              color="success"
              href="https://ludoking.com/"
              target="_blank"
              >Generate Room ID</v-btn
            >
          </BaseCard>
        </v-card>
        <BaseCard heading="Match Status">
          <v-radio-group
            inline
            label="After completion of your game, select the status of the game and post your screenshot below"
            v-model="selectedStatus"
          >
            <v-radio label="I Won" value="Winner" color="success"></v-radio>
            <v-radio label="I Lose" value="Lose" color="red"></v-radio>
            <v-radio label="I CANCEL" value="Cancel" color="warning"></v-radio>
          </v-radio-group>

          <!-- Conditional rendering for image input field -->
          <v-file-input
            v-model="file"
            v-if="selectedStatus == 'Winner'"
            label="Choose an image"
            accept="image/*"
            @change="handleFileUpload"
          ></v-file-input>
          <v-btn
            v-if="selectedStatus == 'Winner'"
            class="text-capitalize element-0"
            color="success"
            @click="uploadImage"
            >Upload Image</v-btn
          >
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-btn
            class="text-capitalize element-0"
            color="success"
            @click="updateStatus()"
          >
            Submit
          </v-btn>
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      playerOneUsername: this.$store.getters.getPlayerOneUsername,
      myBattle: this.$store.getters.getMyBattle,
      roomId: "",
      selectedStatus: "",
      screenshot: null,
      file: null,
    };
  },
  created() {
    this.roomId = this.myBattle.roomId;
    if (this.myBattle.playerOneUsername != this.playerOneUsername) {
      setInterval(() => {
        this.$store.dispatch("setMyBattle", {
          playerOneUsername: this.playerOneUsername,
          playerTwoUsername: this.myBattle.playerOneUsername,
        });
        this.myBattle = this.$store.getters.getMyBattle;
        this.roomId = this.myBattle.roomId;
      }, 2000);
    }
  },
  methods: {
    setRoomId() {
      this.$store.dispatch("setRoomId", this.roomId);
    },
    async updateStatus() {
      if (this.selectedStatus == "Cancel") {
        axios
          .post(this.$store.getters.getBaseUrl + "/game/cancel", {
            gameId: this.myBattle.gameId,
            player1GameStatus: this.myBattle.player1GameStatus,
            player2GameStatus: this.myBattle.player2GameStatus,
            playerOneUsername: this.playerOneUsername,
          })
          .then((response) => {
            window.Swal.fire({
              title: response.data.message,
              icon: "success",
            });
          })
          .catch((error) => {
            window.Swal.fire({
              title: error.response.data.message,
              icon: "error",
            });
          });
      } else {
        axios
          .post(this.$store.getters.getBaseUrl + "/game/update-status", {
            playerOneUsername: this.playerOneUsername,
            player1Status: this.selectedStatus,
          })
          .then((response) => {
            console.log(response);
            window.Swal.fire({
              title: "Status Updated!",
              icon: "success",
            });
          })
          .catch((error) => {
            console.log(error);
            window.Swal.fire({
              title: "Something Went Wrong!",
              icon: "error",
            });
          });
      }
    },
    handleFileUpload(event) {
      this.file = event.target.files[0];
    },
    async uploadImage() {
      if (!this.file) {
        window.Swal.fire({
          title: "Please select a file.",
          icon: "error",
        });
        return;
      }

      const formData = new FormData();
      var fileExtension = this.getFileExtension(this.file.name);
      formData.set(
        "image",
        this.file,
        this.myBattle.gameId + this.playerOneUsername + "." + fileExtension
      );

      try {
        const response = await fetch(
          this.$store.getters.getBaseUrl + "/game/upload",
          {
            method: "POST",
            body: formData,
          }
        );

        if (response.ok) {
          window.Swal.fire({
            title: "Image uploaded successfully",
            icon: "success",
          });
        } else {
          window.Swal.fire({
            title: "Failed to upload image",
            icon: "error",
          });
        }
      } catch (error) {
        console.error("Error uploading image:", error);
      }
    },
    getFileExtension(fileName) {
      const lastIndex = fileName.lastIndexOf(".");
      if (lastIndex === -1) {
        return null;
      }
      return fileName.substr(lastIndex + 1);
    },
  },
};
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.line {
  border-top: 1px solid grey;
  width: 100%;
  margin: 0;
}

.player-card {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.player {
  width: 40%;
  text-align: center;
}

.vs {
  font-size: 24px;
}
</style>
