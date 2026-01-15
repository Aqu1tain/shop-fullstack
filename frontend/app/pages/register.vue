<script setup lang="ts">
const { register } = useAuth()

const username = ref('')
const email = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

async function handleSubmit() {
  error.value = ''
  loading.value = true
  try {
    await register(username.value, email.value, password.value)
    navigateTo('/')
  } catch (e: any) {
    error.value = e.data?.error || 'Registration failed'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <UContainer class="py-16 max-w-md">
    <UCard>
      <template #header>
        <h2 class="text-2xl font-bold">Register</h2>
      </template>

      <form @submit.prevent="handleSubmit" class="space-y-4">
        <UFormField label="Username">
          <UInput v-model="username" required minlength="3" />
        </UFormField>

        <UFormField label="Email">
          <UInput v-model="email" type="email" required />
        </UFormField>

        <UFormField label="Password">
          <UInput v-model="password" type="password" required minlength="6" />
        </UFormField>

        <UAlert v-if="error" color="error" :title="error" />

        <UButton type="submit" block :loading="loading">Register</UButton>
      </form>

      <template #footer>
        <p class="text-center text-sm">
          Already have an account? <NuxtLink to="/login" class="text-primary">Login</NuxtLink>
        </p>
      </template>
    </UCard>
  </UContainer>
</template>
