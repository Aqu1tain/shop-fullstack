<script setup lang="ts">
const { api } = useApi()
const { isAdmin } = useAuth()

const orders = ref<any[]>([])
const loading = ref(true)

const statuses = ['PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED']

const columns = [
  { id: 'id', accessorKey: 'id', header: 'Order #' },
  { id: 'username', accessorKey: 'username', header: 'User' },
  { id: 'productName', accessorKey: 'productName', header: 'Product' },
  { id: 'quantity', accessorKey: 'quantity', header: 'Qty' },
  { id: 'totalAmount', accessorKey: 'totalAmount', header: 'Total' },
  { id: 'orderDate', accessorKey: 'orderDate', header: 'Date' },
  { id: 'status', accessorKey: 'status', header: 'Status' }
]

onMounted(async () => {
  if (!isAdmin.value) {
    navigateTo('/')
    return
  }
  await loadOrders()
})

async function loadOrders() {
  loading.value = true
  try {
    orders.value = await api('/admin/orders')
  } finally {
    loading.value = false
  }
}

async function updateStatus(orderId: number, status: string) {
  await api(`/admin/orders/${orderId}/status?status=${status}`, { method: 'PUT' })
  loadOrders()
}
</script>

<template>
  <UContainer class="py-8">
    <h1 class="text-3xl font-bold mb-6">Orders Management</h1>

    <div v-if="loading" class="text-center py-8">Loading...</div>

    <UCard v-else>
      <UTable :data="orders" :columns="columns">
        <template #totalAmount-cell="{ row }">{{ row.original.totalAmount }} EUR</template>
        <template #orderDate-cell="{ row }">{{ new Date(row.original.orderDate).toLocaleDateString() }}</template>
        <template #status-cell="{ row }">
          <USelect
            :model-value="row.original.status"
            :items="statuses.map(s => ({ label: s, value: s }))"
            @update:model-value="(v) => updateStatus(row.original.id, v as string)"
            size="xs"
          />
        </template>
      </UTable>
    </UCard>
  </UContainer>
</template>
