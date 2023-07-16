package com.example.util;

import java.time.LocalDate;
import java.util.*;

public class RemoveDublicatElementsList {
    public static List<LocalDate> removeDuplicates(List<LocalDate> list) {
        // Set orqali unikal elementlarni saqlash uchun
        Set<LocalDate> set = new HashSet<>();
        Iterator<LocalDate> iterator = list.iterator();
        LinkedList<LocalDate> newList = new LinkedList<>();

        while (iterator.hasNext()) {
            LocalDate element = iterator.next();
            // Agar element avvalroq qo'shilgan bo'lsa, o'chirib tashlash
            if (set.contains(element)) {
                iterator.remove();
            } else {
                set.add(element);
                newList.add(element);
            }
        }
        return newList;
    }
}
