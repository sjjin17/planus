<template>
  <v-container>
    <plan-map />
  </v-container>
</template>

<script>
import PlanMap from "@/components/plans/PlanMap.vue";

export default {
  name: "PlansView",
  components: {
    PlanMap,
  },
  data() {
    return {};
  },
};
</script>

<style></style>
