package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.dto.models.Post

class EditPostResultContract : ActivityResultContract<Post?, Pair<Post?, String?>?>() {
    override fun createIntent(context: Context, input: Post?): Intent =
        Intent(context, NewPostActivity::class.java)
            .putExtra(NewPostActivity.EDIT_POST, input)


    override fun parseResult(resultCode: Int, intent: Intent?): Pair<Post?, String?>? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getSerializableExtra(NewPostActivity.EDIT_POST) as? Post to intent?.getStringExtra(
                NewPostActivity.POST_CONTENT
            )
        } else {
            null
        }
}
