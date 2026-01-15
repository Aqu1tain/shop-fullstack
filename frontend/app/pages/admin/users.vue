<script setup lang="ts">
const { api } = useApi()
const { isAdmin } = useAuth()

const users = ref<any[]>([])
const loading = ref(true)
const showModal = ref(false)
const editing = ref<number | null>(null)
const form = ref({ username: '', email: '', role: '', enabled: true })

const columns = [
  { id: 'id', accessorKey: 'id', header: 'ID' },
  { id: 'username', accessorKey: 'username', header: 'Username' },
  { id: 'email', accessorKey: 'email', header: 'Email' },
  { id: 'role', accessorKey: 'role', header: 'Role' },
  { id: 'enabled', accessorKey: 'enabled', header: 'Status' },
  { id: 'actions', accessorKey: 'actions', header: 'Actions' }
]

onMounted(async () => {
  if (!isAdmin.value) {
    navigateTo('/')
    return
  }
  await loadUsers()
})

async function loadUsers() {
  loading.value = true
  try {
    users.value = await api('/admin/users')
  } finally {
    loading.value = false
  }
}

function openEdit(user: any) {
  editing.value = user.id
  form.value = { ...user }
  showModal.value = true
}

async function handleSubmit() {
  await api(`/admin/users/${editing.value}`, { method: 'PUT', body: form.value })
  showModal.value = false
  loadUsers()
}
</script>

<template>
  <UContainer class="py-8">
    <h1 class="text-3xl font-bold mb-6">Users Management</h1>

    <div v-if="loading" class="text-center py-8">Loading...</div>

    <UCard v-else>
      <UTable :data="users" :columns="columns">
        <template #enabled-cell="{ row }">
          <UBadge :color="row.original.enabled ? 'success' : 'error'">{{ row.original.enabled ? 'Active' : 'Disabled' }}</UBadge>
        </template>
        <template #actions-cell="{ row }">
          <UButton size="xs" variant="outline" @click="openEdit(row.original)">Edit</UButton>
        </template>
      </UTable>
    </UCard>

    <UModal v-model:open="showModal">
      <template #content>
        <UCard>
          <template #header>
            <h2 class="text-xl font-bold">Edit User</h2>
          </template>
          <form @submit.prevent="handleSubmit" class="space-y-4">
            <UFormField label="Username">
              <UInput v-model="form.username" required />
            </UFormField>
            <UFormField label="Email">
              <UInput v-model="form.email" type="email" required />
            </UFormField>
            <UFormField label="Role">
              <USelect v-model="form.role" :items="[{ label: 'USER', value: 'USER' }, { label: 'ADMIN', value: 'ADMIN' }]" />
            </UFormField>
            <UCheckbox v-model="form.enabled" label="Enabled" />
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
