<script setup lang="ts">
const { api } = useApi()
const { isAuthenticated } = useAuth()

const profile = ref<any>(null)
const loading = ref(true)

onMounted(async () => {
  if (!isAuthenticated.value) {
    navigateTo('/login')
    return
  }
  try {
    profile.value = await api('/user/profile')
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <UContainer class="py-8 max-w-xl">
    <h1 class="text-3xl font-bold mb-6">My Profile</h1>

    <div v-if="loading" class="text-center py-8">Loading...</div>

    <UCard v-else-if="profile">
      <div class="space-y-4">
        <div>
          <label class="text-sm text-gray-500">Username</label>
          <p class="font-medium">{{ profile.username }}</p>
        </div>
        <div>
          <label class="text-sm text-gray-500">Email</label>
          <p class="font-medium">{{ profile.email }}</p>
        </div>
        <div>
          <label class="text-sm text-gray-500">Role</label>
          <UBadge :color="profile.role === 'ADMIN' ? 'primary' : 'neutral'">{{ profile.role }}</UBadge>
        </div>
        <div>
          <label class="text-sm text-gray-500">Member since</label>
          <p class="font-medium">{{ new Date(profile.createdAt).toLocaleDateString() }}</p>
        </div>
      </div>
    </UCard>
  </UContainer>
</template>
