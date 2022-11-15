<template>
  <v-container>
    <plan-card
      v-for="(plan, i) in completeList"
      :key="i + 'c'"
      :plan="plan"
    ></plan-card>
  </v-container>
</template>

<script>
import PlanCard from "@/components/complete/planCard.vue";
export default {
  name: "CompletePage",
  components: {
    PlanCard,
  },
  props: {
    completeList: Array,
  },
};
</script>

<style></style>
