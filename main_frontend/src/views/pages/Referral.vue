<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Your Referral Code">
          <v-sheet class="mx-auto" max-width="600">
            <v-slide-group show-arrows>
              <v-slide-group-item v-for="n in 25" :key="n" v-slot="{ isSelected, toggle }">
                <v-btn :color="isSelected ? 'primary' : undefined" class="ma-2" rounded @click="toggle">
                  Options {{ n }}
                </v-btn>
              </v-slide-group-item>
            </v-slide-group>
          </v-sheet>
          <v-card-title class="inv-text">{{ myReferralCode }}</v-card-title>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn prepend-icon="mdi-content-copy" class="text-capitalize element-0" color="success" @click="copyCode" @mouseout="reset"
                style="cursor: pointer;" v-bind="attrs" v-on="on">Copy</v-btn>
            </template>
            <span>{{ copyText }}</span>
          </v-tooltip> 
          <v-btn icon @click="shareOnSocialMedia('whatsapp')">
              <v-icon>mdi-whatsapp</v-icon>
          </v-btn>
          <v-btn icon @click="shareOnSocialMedia('facebook')">
              <v-icon>mdi-facebook</v-icon>
          </v-btn>
          <v-btn icon @click="shareOnSocialMedia('instagram')">
              <v-icon>mdi-instagram</v-icon>
          </v-btn>
          <!-- <v-btn prepend-icon="mdi-whatsapp" class="text-capitalize element-0" color="success" @click="shareOnSocialMedia">Share on Social Media</v-btn> -->
        </BaseCard>
        <BaseCard heading="Referrals">
          <Referral_table></Referral_table>
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  name: "CopyToClipBoard",
  data() {
    return {
      text: "Copy to invite",
      copyText: "",
      myReferralCode: "",
      phoneNumber: this.$store.getters.getRegPhoneNumber,
    };
  },
  created() {
    this.getMyReferralCode();
    this.copyText = this.text;
  },
  components: {
    Referral_table: () =>
      import("@/components/vuetifyComponents/tables-simple/Referral_table"),
  },
  methods: {
    async copyCode() {
      await navigator.clipboard.writeText(
        "Hello!!\nPlay Ludo and earn Rs10000/- daily.\nCommission Charge - 5% Only\nReferral - 2% On All Games\n24x7 Live Chat Support\nInstant Withdrawal Via UPI/Bank\nRegister Now, My refer code is " +
        this.myReferralCode +
        "\n👇👇\nhttp://karniludoclub.com:8080/#/home"
      );
      this.copyText = "Copied";
    },
    reset() {
      this.copyText = this.text;
    },
    getMyReferralCode() {
      axios
        .post(this.$store.getters.getBaseUrl + "/referral/my-referral-code", {
          phoneNumber: "+91" + this.phoneNumber,
        })
        .then((response) => {
          this.myReferralCode = response.data.refereeCode;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    shareOnSocialMedia(platform) {
  const referralMessage = encodeURIComponent(
    `Hello!!\nPlay Ludo and earn Rs10000/- daily.\nCommission Charge - 5% Only\nReferral - 2% On All Games\n24x7 Live Chat Support\nInstant Withdrawal Via UPI/Bank\nRegister Now, My referral code is ${this.myReferralCode}\nPlay now👇👇\nhttp://karniludoclub.com:8080/#/home`
  );

  let socialMediaURL = '';
  switch (platform) {
    case 'whatsapp':
      socialMediaURL = `https://wa.me/?text=${referralMessage}`;
      break;
    case 'facebook':
      socialMediaURL = `https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent('http://karniludoclub.com:8080/#/home')}&quote=${referralMessage}`;
      break;
    case 'instagram':
      socialMediaURL = `https://www.instagram.com/share?url=${encodeURIComponent('http://karniludoclub.com:8080/#/home')}&title=${referralMessage}`;
      break;
    default:
      break;
  }

  if (socialMediaURL !== '') {
    window.open(socialMediaURL);
  }
}


  },
};
</script>
