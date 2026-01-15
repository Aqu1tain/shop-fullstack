<script setup lang="ts">
const { api } = useApi()
const { isAdmin } = useAuth()

const stats = ref<any>(null)
const loading = ref(true)

onMounted(async () => {
  if (!isAdmin.value) {
    navigateTo('/')
    return
  }
  try {
    stats.value = await api('/admin/dashboard')
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <UContainer class="py-8">
    <h1 class="text-3xl font-bold mb-6">Admin Dashboard</h1>

    <div class="flex gap-4 mb-8">
      <UButton to="/admin/products">Manage Products</UButton>
      <UButton to="/admin/users">Manage Users</UButton>
      <UButton to="/admin/orders">Manage Orders</UButton>
    </div>

    <div v-if="loading" class="text-center py-8">Loading...</div>

    <div v-else-if="stats" class="grid grid-cols-2 lg:grid-cols-4 gap-6">
      <UCard class="text-center">
        <p class="text-sm text-gray-500">Total Users</p>
        <p class="text-4xl font-bold">{{ stats.totalUsers }}</p>
      </UCard>
      <UCard class="text-center">
        <p class="text-sm text-gray-500">Total Products</p>
        <p class="text-4xl font-bold">{{ stats.totalProducts }}</p>
      </UCard>
      <UCard class="text-center">
        <p class="text-sm text-gray-500">Total Orders</p>
        <p class="text-4xl font-bold">{{ stats.totalOrders }}</p>
      </UCard>
      <UCard class="text-center">
        <p class="text-sm text-gray-500">Pending Orders</p>
        <p class="text-4xl font-bold">{{ stats.pendingOrders }}</p>
      </UCard>
      <UCard class="text-center col-span-full">
        <p class="text-sm text-gray-500">Total Revenue</p>
        <p class="text-4xl font-bold text-green-500">{{ stats.totalRevenue }} EUR</p>
      </UCard>
    </div>
  </UContainer>
</template>
