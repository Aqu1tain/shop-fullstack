<script setup lang="ts">
const { api } = useApi()
const { isAuthenticated } = useAuth()

const orders = ref<any[]>([])
const loading = ref(true)

const statusColors: Record<string, string> = {
  PENDING: 'warning',
  PROCESSING: 'info',
  SHIPPED: 'primary',
  DELIVERED: 'success'
}

const columns = [
  { id: 'id', accessorKey: 'id', header: 'Order #' },
  { id: 'productName', accessorKey: 'productName', header: 'Product' },
  { id: 'quantity', accessorKey: 'quantity', header: 'Qty' },
  { id: 'totalAmount', accessorKey: 'totalAmount', header: 'Total' },
  { id: 'orderDate', accessorKey: 'orderDate', header: 'Date' },
  { id: 'status', accessorKey: 'status', header: 'Status' }
]

onMounted(async () => {
  if (!isAuthenticated.value) {
    navigateTo('/login')
    return
  }
  try {
    orders.value = await api('/orders/my-orders')
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <UContainer class="py-8">
    <h1 class="text-3xl font-bold mb-6">My Orders</h1>

    <div v-if="loading" class="text-center py-8">Loading...</div>

    <UCard v-else-if="orders.length === 0" class="text-center py-8">
      <p class="mb-4">No orders yet.</p>
      <UButton to="/">Browse Products</UButton>
    </UCard>

    <UCard v-else>
      <UTable :data="orders" :columns="columns">
        <template #totalAmount-cell="{ row }">{{ row.original.totalAmount }} EUR</template>
        <template #orderDate-cell="{ row }">{{ new Date(row.original.orderDate).toLocaleDateString() }}</template>
        <template #status-cell="{ row }">
          <UBadge :color="statusColors[row.original.status] || 'neutral'">{{ row.original.status }}</UBadge>
        </template>
      </UTable>
    </UCard>
  </UContainer>
</template>
