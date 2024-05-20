<template>
    <div>
        <!-- Fixed navbar with icons at the bottom -->
        <v-bottom-navigation app fixed class="bottom-nav">
            <router-link to="/dashboard">
                <v-btn icon>
                    <v-icon>mdi-home</v-icon>
                </v-btn>
            </router-link>
            <router-link to="/dashboard/games">
                <v-btn icon>
                    <v-icon>mdi-dice-multiple</v-icon>
                </v-btn>
            </router-link>
            <router-link to="/dashboard/transactions">
                <v-btn icon>
                    <v-icon>mdi-bank-transfer</v-icon>
                </v-btn>
            </router-link>
            <router-link to="/dashboard/wallet">
                <v-btn icon>
                    <v-icon>mdi-wallet</v-icon>
                </v-btn>
            </router-link>
        </v-bottom-navigation>
    </div>
</template>

<style scoped>
.bottom-nav {
    background-color: #2196F3;
    /* Set the background color of the bottom navigation */
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: 8px 0;
    /* Adjust the padding as needed */
}

.icon-link {
    text-decoration: none;
    color: white;
}
</style>