<script setup lang="ts">
const { api } = useApi()
const { isAdmin } = useAuth()

const products = ref<any[]>([])
const categories = ref<any[]>([])
const loading = ref(true)
const showModal = ref(false)
const editing = ref<number | null>(null)
const form = ref({ name: '', description: '', price: '', categoryId: null as number | null, stockQuantity: '', imageUrl: '' })

const columns = [
  { id: 'id', accessorKey: 'id', header: 'ID' },
  { id: 'name', accessorKey: 'name', header: 'Name' },
  { id: 'categoryName', accessorKey: 'categoryName', header: 'Category' },
  { id: 'price', accessorKey: 'price', header: 'Price' },
  { id: 'stockQuantity', accessorKey: 'stockQuantity', header: 'Stock' },
  { id: 'actions', accessorKey: 'actions', header: 'Actions' }
]

onMounted(async () => {
  if (!isAdmin.value) {
    navigateTo('/')
    return
  }
  await loadData()
})

async function loadData() {
  loading.value = true
  try {
    const [p, c] = await Promise.all([
      api<any[]>('/admin/products'),
      api<any[]>('/admin/categories')
    ])
    products.value = p
    categories.value = c
  } finally {
    loading.value = false
  }
}

function openCreate() {
  editing.value = null
  form.value = { name: '', description: '', price: '', categoryId: null, stockQuantity: '', imageUrl: '' }
  showModal.value = true
}

function openEdit(product: any) {
  editing.value = product.id
  form.value = { ...product }
  showModal.value = true
}

async function handleSubmit() {
  if (editing.value) {
    await api(`/admin/products/${editing.value}`, { method: 'PUT', body: form.value })
  } else {
    await api('/admin/products', { method: 'POST', body: form.value })
  }
  showModal.value = false
  loadData()
}

async function handleDelete(id: number) {
  if (!confirm('Delete this product?')) return
  await api(`/admin/products/${id}`, { method: 'DELETE' })
  loadData()
}
</script>

<template>
  <UContainer class="py-8">
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-3xl font-bold">Products Management</h1>
      <UButton @click="openCreate">Add Product</UButton>
    </div>

    <div v-if="loading" class="text-center py-8">Loading...</div>

    <UCard v-else>
      <UTable :data="products" :columns="columns">
        <template #price-cell="{ row }">{{ row.original.price }} EUR</template>
        <template #actions-cell="{ row }">
          <div class="flex gap-2">
            <UButton size="xs" variant="outline" @click="openEdit(row.original)">Edit</UButton>
            <UButton size="xs" color="error" variant="outline" @click="handleDelete(row.original.id)">Delete</UButton>
          </div>
        </template>
      </UTable>
    </UCard>

    <UModal v-model:open="showModal">
      <template #content>
        <UCard>
          <template #header>
            <h2 class="text-xl font-bold">{{ editing ? 'Edit Product' : 'Add Product' }}</h2>
          </template>
          <form @submit.prevent="handleSubmit" class="space-y-4">
            <UFormField label="Name">
              <UInput v-model="form.name" required />
            </UFormField>
            <UFormField label="Description">
              <UTextarea v-model="form.description" />
            </UFormField>
            <UFormField label="Price">
              <UInput v-model="form.price" type="number" step="0.01" required />
            </UFormField>
            <UFormField label="Category">
              <USelect v-model="form.categoryId" :items="categories.map((c: any) => ({ label: c.name, value: c.id }))" placeholder="Select category" />
            </UFormField>
            <UFormField label="Stock">
              <UInput v-model="form.stockQuantity" type="number" />
            </UFormField>
            <UFormField label="Image URL">
              <UInput v-model="form.imageUrl" />
            </UFormField>
            <div class="flex gap-2">
              <UButton type="submit">Save</UButton>
              <UButton variant="outline" @click="showModal = false">Cancel</UButton>
            </div>
          </form>
        </UCard>
      </template>
    </UModal>
  </UContainer>
</template>
