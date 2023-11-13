import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useMemberStore = defineStore('memberStore', () => {
  const memberId = ref("")
  const memberName = ref("")

  function nameUpdate(newName) {
    memberName = newName
  }

  return { memberId, memberName, nameUpdate }
}, { persist: {storage: sessionStorage} })
