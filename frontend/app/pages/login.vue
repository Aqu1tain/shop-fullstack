<script setup lang="ts">
const { login } = useAuth()

const username = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

async function handleSubmit() {
  error.value = ''
  loading.value = true
  try {
    await login(username.value, password.value)
    navigateTo('/')
  } catch (e: any) {
    error.value = e.data?.error || 'Login failed'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <UContainer class="py-16 max-w-md">
    <UCard>
      <template #header>
        <h2 class="text-2xl font-bold">Login</h2>
      </template>

      <form @submit.prevent="handleSubmit" class="space-y-4">
        <UFormField label="Username">
          <UInput v-model="username" required />
        </UFormField>

        <UFormField label="Password">
          <UInput v-model="password" type="password" required />
        </UFormField>

        <UAlert v-if="error" color="error" :title="error" />

        <UButton type="submit" block :loading="loading">Login</UButton>
      </form>

      <template #footer>
        <p class="text-center text-sm">
          Don't have an account? <NuxtLink to="/register" class="text-primary">Register</NuxtLink>
        </p>
      </template>
    </UCard>
  </UContainer>
</template>
